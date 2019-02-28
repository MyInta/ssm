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
	 * ��ѯ���з���
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
	 * ���һ������
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����װ������category
		 * 2������service��add����
		 * 3������findAll��������list��ʾ���з���
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());//����cid
		categoryService.add(parent);
		return findAll(req,resp);
	}
	/**
	 * ����ӷ���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����װ������category
		 * 2����Ҫ�ֶ������е�pidӳ�䵽child������
		 * 3������service��add����
		 * 4������findAll��������list��ʾ���з���
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());//����cid
		
		//�ֶ�ӳ��pid,pidҪ��parent��װ��
		String pid =req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.add(child);
		return findAll(req,resp);
	}
	/**
	 * ��ӵڶ����ࣺ��һ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = req.getParameter("pid");//��õ�ǰ����ĸ�����id
		List<Category> parents = categoryService.findParents();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		
		return "/adminjsps/admin/category/add2.jsp";
	}
	
	/**
	 * �޸�һ�������һ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����ȡ�����е�cid
		 * 2��ʹ��cid����category
		 * 3������category
		 * 4��ת����edit.jspҳ����ʾcategory
		 */
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "/adminjsps/admin/category/edit.jsp";
	}
	
	/**
	 * �޸�һ������ڶ���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����װ�����ݵ�category��
		 * 2������service��������޸�
		 * 3��ת����list.jsp��ʾ���з��ࣨreturn findAll()��
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAll(req,resp);
	}
	/**
	 * �޸Ķ��������һ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1��ͨ������cid ��ѯ��category������֮
		 * 2����ѯ������һ�����࣬����֮
		 * 3��ת����edit2.jsp
		 */
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findParents());
		return "/adminjsps/admin/category/edit2.jsp";
		
	}
	/**
	 * �޸Ķ�������ڶ���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����װ����category child
		 * 2���ѱ�pid��װ��child
		 * 3������service���޸ķ���edit()
		 * 4�����ص�list.jsp
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
	 * ɾ��һ������
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1��ͨ��cid��ѯ���������µ��ӷ�������
		 * 2�������������0��˵�������ӷ��࣬���������Ϣ��ת��msg.jsp
		 * 3���������0��ɾ��֮
		 */
		String cid = req.getParameter("cid");
		int childCount = categoryService.findChildrenCountByParent(cid);
		if(childCount>0) {
			req.setAttribute("msg", "�����ӷ���δɾ���ɾ������ܽ���ɾ�����������");
			return "/adminjsps/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(req,resp);
		}
		
	}
	/**
	 * ɾ����������
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����ȡcid,2�������id
		 * 2������÷����µ�ͼ����Ŀ>0,���������Ϣ��ת����msg.jsp
		 * ����ɾ��֮�������ص�list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if(cnt>0) {
			req.setAttribute("msg", "��Ŀ¼�»���ͼ�飬���ܽ���ɾ��������");
			return "/adminjsps/msg.jsp";
		}else {
			categoryService.delete(cid);
			return findAll(req,resp);
		}
	}
}
