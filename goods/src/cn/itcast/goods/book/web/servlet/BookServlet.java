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
 * servlet中注入service
 * @author 银涛
 *
 */
public class BookServlet extends BaseServlet {
	
	private BookService bookService = new BookService();
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
	 * 按图书bid查找
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
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
		return "/jsps/book/list.jsp";
	}
}
