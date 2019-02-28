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
	 * ɾ��ͼ��
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
		//��׳�ԣ��������ݿ���ͼƬ��ַ�Ƿ�Ϊ��
		if(null!=book.getImage_w()) {
			File imageFile = new File(savePath,book.getImage_w());//ɾ����ͼ
			imageFile.delete();
		}
		if(null!=book.getImage_b()) {
			File imageFile = new File(savePath,book.getImage_b());//ɾ��Сͼ
			imageFile.delete();
		}
		bookService.delete(bid);
		return "/adminjsps/msg.jsp";
	}
	
	/**
	 * �޸�ͼ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1���ѱ����ݷ�װ��Book������
		 * 2����װcid��Category��
		 * 3����Category����Book
		 * 4������service��ɹ���
		 * 5������ɹ���Ϣ��ת����msg.jsp
		 */
		Map<String,String[]> map = req.getParameterMap();
		Book book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		
		bookService.edit(book);
		req.setAttribute("msg", "�޸�ͼ��ɹ���");
		return "/adminjsps/msg.jsp";
		
	}
	
	/**
	 * ����ͼ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1����ȡbid���õ�Book���󣬱���֮
		 */
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		/**
		 * 2����ȡ����һ�����࣬����֮
		 */
		req.setAttribute("parents", categoryService.findParents());
		/**
		 * 3����ȡ��ǰͼ��һ�������µ����ж�������
		 */
		String pid = book.getCategory().getParent().getCid();
		req.setAttribute("children", categoryService.findChildren(pid));
		/**
		 * 4��ת����desc.jsp��ʾ
		 */
		return "/adminjsps/admin/book/desc.jsp";
	}
	
	/**
	 * ���ͼ���һ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1����ȡһ�����ౣ��֮
		 * 2�����ص�add.jsp ��ҳ���»��������б�����ʾ����һ������
		 */
		List<Category> parents = categoryService.findParents();
		req.setAttribute("parents", parents);
		return "/adminjsps/admin/book/add.jsp";
	}
	
	/**
	 * ��ȡһ�������µ����ж�������
	 * 
	 * @param req
	 * @param resp
	 * @return ��������list��json����
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1����ȡpid
		 * 2��ͨ��pid��ѯ�����ж�������
		 * 3����List<Category>ת����json���ݣ�������ͻ���
		 */
		String pid = req.getParameter("pid");
		List<Category> children = categoryService.findChildren(pid);
		String json = toJson(children);
		
		resp.getWriter().print(json);
		return null;
	}
	/**
	 * �����б����� ת��json���� ��ʽ���ͣ�{"xxx":"sfas","yyy":"sgdfdg"}
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
	 * �����б����� ת��json���� ��ʽ���ͣ�[{"xxx":"sfas","yyy":"sgdfdg"},{"xxx":"sfas","yyy":"sgdfdg"}]
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
	 * ��ʾ���з���
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
	 * ��ȡpc Ĭ��Ϊ1����req����ȡ��ֵ���ø�ֵ
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
	 * ��ȡurl,��ҳ��������Ҫ����Ϊ������Ŀ��
	 * http://localhost:8080/goods/BookServlet?method=findByCategory&cid=xxx
	 * getRequestURI() --> /goods/BookServlet
	 * getQueryString() -->method=findByCategory&cid=xxx
	 * @param req
	 * @return
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI()+"?"+req.getQueryString();//��ǰ������ַ���
		/*
		 * �������pc��������Ҫ�����ȡ��
		 */
		int index = url.indexOf("&pc=");
		if(index!=-1) {//�������������url��
			url = url.substring(0,index);
		}
		return url;
	}
	
	/**
	 * �������ѯ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1���õ�pc,ҳ�洫ʹ��ҳ�棬û��Ĭ��pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2���õ�url..
		 */
		String url = getUrl(req);
		/**
		 *3����ȡ��ѯ����������������cid���������id
		 */
		String cid = req.getParameter("cid");
		/**
		 * 4��ʹ��pc��cid����service��findByCategory�������PageBean
		 */
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/**
		 * 5����PageBean����url,ת����/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * �����߲�ѯ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1���õ�pc,ҳ�洫ʹ��ҳ�棬û��Ĭ��pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2���õ�url..
		 */
		String url = getUrl(req);
		/**
		 *3����ȡ��ѯ����
		 *new String(request.getParameter("name").getBytes("iso-8859-1"),"�ͻ��˱��뷽ʽ");
		 */
		String author = req.getParameter("author");
		/**
		 * 4������service��findByAuthor�������PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/**
		 * 5����PageBean����url,ת����/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * ���������ѯ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1���õ�pc,ҳ�洫ʹ��ҳ�棬û��Ĭ��pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2���õ�url..
		 */
		String url = getUrl(req);
		/**
		 *3����ȡ��ѯ����
		 */
		String press = req.getParameter("press");
		/**
		 * 4������service��findByCategory�������PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/**
		 * 5����PageBean����url,ת����/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * ��������ѯ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1���õ�pc,ҳ�洫ʹ��ҳ�棬û��Ĭ��pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2���õ�url..
		 */
		String url = getUrl(req);
		/**
		 *3����ȡ��ѯ����
		 */
		String bname = req.getParameter("bname");
		/**
		 * 4������service��findByCategory�������PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		/**
		 * 5����PageBean����url,ת����/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * ���ۺ���Ϣ��ѯ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1���õ�pc,ҳ�洫ʹ��ҳ�棬û��Ĭ��pc=1
		 */
		int pc = getPc(req);
		/**
		 * 2���õ�url..
		 */
		String url = getUrl(req);
		/**
		 *3����ȡ��ѯ����
		 */
		Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/**
		 * 4������service��findByCategory�������PageBean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/**
		 * 5����PageBean����url,ת����/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/book/list.jsp";
	}
}
