package cn.itcast.goods.book.web.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;
/**
 * servlet��ע��service
 * @author ����
 *
 */
public class BookServlet extends BaseServlet {
	
	private BookService bookService = new BookService();
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
	 * ��ͼ��bid����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");
		Book book = bookService.load(bid);
		req.setAttribute("book", book);
		return "/jsps/book/desc.jsp";
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
	}
}
