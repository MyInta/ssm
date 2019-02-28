package cn.itcast.goods.admin.book.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.category.service.CategoryService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

public class AdminBookServlet extends BaseServlet {

	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	/**
	 * 删除图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		String savePath = this.getServletContext().getRealPath("/");
		//健壮性，考虑数据库内图片地址是否为空
		if(null!=book.getImage_w()) {
			File imageFile = new File(savePath,book.getImage_w());//删除大图
			imageFile.delete();
		}
		if(null!=book.getImage_b()) {
			File imageFile = new File(savePath,book.getImage_b());//删除小图
			imageFile.delete();
		}
		bookService.delete(bid);
		return "/adminjsps/msg.jsp";
	}
	
	/**
	 * 修改图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1、把表单数据封装到Book对象中
		 * 2、封装cid到Category中
		 * 3、把Category赋给Book
		 * 4、调用service完成工作
		 * 5、保存成功信息，转发到msg.jsp
		 */
		Map<String,String[]> map = req.getParameterMap();
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		
		bookService.edit(book);
		req.setAttribute("msg", "修改图书成功！");
		return "/adminjsps/msg.jsp";
		
	}
	
	/**
	 * 加载图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1、获取bid，得到Book对象，保存之
		 */
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		/**
		 * 2、获取所有一级分类，保存之
		 */
		req.setAttribute("parents", categoryService.findParents());
		/**
		 * 3、获取当前图书一级分类下的所有二级分类
		 */
		String pid = book.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));
		/**
		 * 4、转发到desc.jsp显示
		 */
		return "/adminjsps/admin/book/desc.jsp";
	}
	
	/**
	 * 添加图书第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1、获取一级分类保存之
		 * 2、返回到add.jsp 该页面下会在下拉列表中显示所有一级分类
		 */
		List<Category> parents = categoryService.findParents();
		req.setAttribute("parents", parents);
		return "/adminjsps/admin/book/add.jsp";
	}
	
	/**
	 * 获取一级分类下的所有二级分类
	 * 
	 * @param req
	 * @param resp
	 * @return 二级分类list的json对象
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1、获取pid
		 * 2、通过pid查询出所有二级分类
		 * 3、将List<Category>转换成json数据，输出给客户端
		 */
		String pid = req.getParameter("pid");
		List<Category> children = categoryService.findChildren(pid);
		String json = toJson(children);
		
		resp.getWriter().print(json);
		return null;
	}
	/**
	 * 将表单列表数据 转成json数据 格式类型：{"xxx":"sfas","yyy":"sgdfdg"}
	 * @param category
	 * @return
	 */
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	/**
	 * 将表单列表数据 转成json数据 格式类型：[{"xxx":"sfas","yyy":"sgdfdg"},{"xxx":"sfas","yyy":"sgdfdg"}]
	 * @param categoryList
	 * @return
	 */
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for(int i=0;i<categoryList.size();i++) {
			sb.append(toJson(categoryList.get(i)));
			if(i<categoryList.size()-1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 显示所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		List<Category> parents = categoryService.findAll();
		req.setAttribute("parents", parents);
		return "/adminjsps/admin/book/left.jsp";
	}
	/**
	 * 获取pc 默认为1，若req中能取到值则用该值
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if(param!=null&&!param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch (RuntimeException e) {
			}
		}
		return pc;
	}
	/**
	 * 获取url,分页导航中需要它作为超连接目标
	 * http://localhost:8080/goods/BookServlet?method=findByCategory&cid=xxx
	 * getRequestURI() --> /goods/BookServlet
	 * getQueryString() -->method=findByCategory&cid=xxx
	 * @param req
	 * @return
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI()+"?"+req.getQueryString();//当前请求的字符串
		/*
		 * 如果存在pc参数，需要将其截取掉
		 */
		int index = url.indexOf("&pc=");
		if(index!=-1) {//如果该索引存在url中
			url = url.substring(0,index);
		}
		return url;
	}
	
	/**
	 * 按分类查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、得到pc,页面传使用页面，没传默认pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2、得到url..
		 */
		String url = getUrl(req);
		/**
		 *3、获取查询条件，本方法就是cid，即分类的id
		 */
		String cid = req.getParameter("cid");
		/**
		 * 4、使用pc和cid调用service的findByCategory方法获得PageBean
		 */
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/**
		 * 5、给PageBean设置url,转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * 按作者查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、得到pc,页面传使用页面，没传默认pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2、得到url..
		 */
		String url = getUrl(req);
		/**
		 *3、获取查询条件
		 *new String(request.getParameter("name").getBytes("iso-8859-1"),"客户端编码方式");
		 */
		String author = req.getParameter("author");
		/**
		 * 4、调用service的findByAuthor方法获得PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/**
		 * 5、给PageBean设置url,转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * 按出版社查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、得到pc,页面传使用页面，没传默认pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2、得到url..
		 */
		String url = getUrl(req);
		/**
		 *3、获取查询条件
		 */
		String press = req.getParameter("press");
		/**
		 * 4、调用service的findByCategory方法获得PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/**
		 * 5、给PageBean设置url,转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * 按书名查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、得到pc,页面传使用页面，没传默认pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2、得到url..
		 */
		String url = getUrl(req);
		/**
		 *3、获取查询条件
		 */
		String bname = req.getParameter("bname");
		/**
		 * 4、调用service的findByCategory方法获得PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		/**
		 * 5、给PageBean设置url,转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * 按综合信息查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、得到pc,页面传使用页面，没传默认pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2、得到url..
		 */
		String url = getUrl(req);
		/**
		 *3、获取查询条件
		 */
		Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/**
		 * 4、调用service的findByCategory方法获得PageBean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/**
		 * 5、给PageBean设置url,转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
}
