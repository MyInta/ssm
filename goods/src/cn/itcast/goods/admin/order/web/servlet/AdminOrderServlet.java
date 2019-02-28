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
	 * ��ѯ����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
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
		 *3���ӵ�ǰsession�л�ȡ�û�
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		/**
		 * 4��ʹ��pc��cid����service��findByCategory�������PageBean
		 */
		PageBean<Order> pb = orderService.findAll(pc);
		/**
		 * 5����PageBean����url,ת����/jsps/order/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/order/list.jsp";
	}
	
	/**
	 * ��״̬��ѯ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
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
		 *3���������״̬
		 */
		int status = Integer.parseInt(req.getParameter("status"));
		/**
		 * 4��ʹ��pc��cid����service��findByCategory�������PageBean
		 */
		PageBean<Order> pb = orderService.findByStatus(status,pc);
		/**
		 * 5����PageBean����url,ת����/jsps/order/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "/adminjsps/admin/order/list.jsp";
	}
	/**
	 * �鿴������ϸ��Ϣ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//�������
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		//������һ����ť���ӽ�����
		String btn = req.getParameter("btn");
		req.setAttribute("btn", btn);
		return "/adminjsps/admin/order/desc.jsp";
	}
	
	/**
	 * ȡ������
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
		 * У��״̬
		 * �����Ϊ1����ʾ����
		 */
		int status = orderService.findStatus(oid);
		if(1!=status) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "״̬���ԣ�����ȡ����");
			return "/adminjsps/admin/msg.jsp";
		}
		//���״̬Ϊ1����Խ���ȡ��,����״̬����Ϊ5�����״̬
		orderService.updateStatus(oid, 5);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "���Ķ�����ȡ������ӭ�ٴ�ѡ����");
		return "/adminjsps/admin/msg.jsp";
	}
	/**
	 * ��������
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
		 * У��״̬
		 * �����Ϊ2����ʾ����
		 */
		int status = orderService.findStatus(oid);
		if(2!=status) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "״̬���ԣ����ܷ�����");
			return "/adminjsps/admin/msg.jsp";
		}
		//���״̬Ϊ1����Խ���ȡ��,����״̬����Ϊ5�����״̬
		orderService.updateStatus(oid, 3);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "���Ķ����ѷ������뼰ʱ�ջ�ȷ�ϣ�");
		return "/adminjsps/admin/msg.jsp";
	}
}
