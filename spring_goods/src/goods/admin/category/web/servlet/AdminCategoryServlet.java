package goods.admin.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import goods.book.service.BookService;
import goods.category.domain.Category;
import goods.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

public class AdminCategoryServlet extends BaseServlet{

	private CategoryService categoryService = new CategoryService();
	
	private BookService bookService = new BookService();
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("parents", categoryService.findAll());
		return "/adminjsps/admin/category/list.jsp";
	}
	
	/**
	 * 添加一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、封装表单数据category
		 * 2、调用service的add方法
		 * 3、调用findAll方法返回list显示所有分类
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());//设置cid
		categoryService.add(parent);
		return findAll(req,resp);
	}
	/**
	 * 添加子分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、封装表单数据category
		 * 2、需要手动将表单中的pid映射到child对象中
		 * 3、调用service的add方法
		 * 4、调用findAll方法返回list显示所有分类
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());//设置cid
		
		//手动映射pid,pid要用parent来装载
		String pid =req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.add(child);
		return findAll(req,resp);
	}
	/**
	 * 添加第二分类：第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = req.getParameter("pid");//获得当前点击的父分类id
		List<Category> parents = categoryService.findParents();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		
		return "/adminjsps/admin/category/add2.jsp";
	}
	
	/**
	 * 修改一级分类第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、获取链接中的cid
		 * 2、使用cid加载category
		 * 3、保存category
		 * 4、转发到edit.jsp页面显示category
		 */
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "/adminjsps/admin/category/edit.jsp";
	}
	
	/**
	 * 修改一级分类第二部
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、封装表单数据到category中
		 * 2、调用service方法完成修改
		 * 3、转发到list.jsp显示所有分类（return findAll()）
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAll(req,resp);
	}
	/**
	 * 修改二级分类第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、通过参数cid 查询出category，保存之
		 * 2、查询出所有一级分类，保存之
		 * 3、转发到edit2.jsp
		 */
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findParents());
		return "/adminjsps/admin/category/edit2.jsp";
		
	}
	/**
	 * 修改二级分类第二步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、封装表单到category child
		 * 2、把表单pid封装到child
		 * 3、调用service的修改方法edit()
		 * 4、返回到list.jsp
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		String pid =req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.edit(child);
		return findAll(req,resp);
	}
	
	/**
	 * 删除一级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、通过cid查询出父分类下的子分类数量
		 * 2、如果个数大于0，说明还有子分类，保存错误信息，转发msg.jsp
		 * 3、如果等于0，删除之
		 */
		String cid = req.getParameter("cid");
		int childCount = categoryService.findChildrenCountByParent(cid);
		if(childCount>0) {
			req.setAttribute("msg", "还有子分类未删除干净！不能进行删除父分类操作");
			return "/adminjsps/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(req,resp);
		}
		
	}
	/**
	 * 删除二级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、获取cid,2级分类的id
		 * 2、如果该分类下的图书数目>0,保存错误信息，转发到msg.jsp
		 * 否则删除之，并返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if(cnt>0) {
			req.setAttribute("msg", "该目录下还有图书，不能进行删除操作！");
			return "/adminjsps/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(req,resp);
		}
	}
}
