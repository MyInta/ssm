package cn.itcast.goods.admin.order.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.goods.order.domain.Order;
import cn.itcast.goods.order.service.OrderService;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	
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
	 * 查询所有
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
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
		 *3、从当前session中获取用户
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		/**
		 * 4、使用pc和cid调用service的findByCategory方法获得PageBean
		 */
		PageBean<Order> pb = orderService.findAll(pc);
		/**
		 * 5、给PageBean设置url,转发到/jsps/order/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * 按状态查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
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
		 *3、获得链接状态
		 */
		int status = Integer.parseInt(req.getParameter("status"));
		/**
		 * 4、使用pc和cid调用service的findByCategory方法获得PageBean
		 */
		PageBean<Order> pb = orderService.findByStatus(status,pc);
		/**
		 * 5、给PageBean设置url,转发到/jsps/order/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/order/list.jsp";
	}
	/**
	 * 查看订单详细信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//订单编号
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		//是用哪一个按钮链接进来的
		String btn = req.getParameter("btn");
		req.setAttribute("btn", btn);
		return "/adminjsps/admin/order/desc.jsp";
	}
	
	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/**
		 * 校验状态
		 * 如果不为1则显示错误
		 */
		int status = orderService.findStatus(oid);
		if(1!=status) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "/adminjsps/admin/msg.jsp";
		}
		//如果状态为1则可以进行取消,并将状态设置为5已完成状态
		orderService.updateStatus(oid, 5);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已取消，欢迎再次选购！");
		return "/adminjsps/admin/msg.jsp";
	}
	/**
	 * 发货功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deliver(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		/**
		 * 校验状态
		 * 如果不为2则显示错误
		 */
		int status = orderService.findStatus(oid);
		if(2!=status) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能发货！");
			return "/adminjsps/admin/msg.jsp";
		}
		//如果状态为1则可以进行取消,并将状态设置为5已完成状态
		orderService.updateStatus(oid, 3);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已发货，请及时收货确认！");
		return "/adminjsps/admin/msg.jsp";
	}
}
