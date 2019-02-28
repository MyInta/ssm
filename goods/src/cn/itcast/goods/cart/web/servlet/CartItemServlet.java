package cn.itcast.goods.cart.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.cart.domain.CartItem;
import cn.itcast.goods.cart.service.CartItemService;
import cn.itcast.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;

public class CartItemServlet extends BaseServlet{

	private CartItemService cartItemService = new CartItemService();
	
	public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1���ҵ�����cartItemIds
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		//�����ܼ�ֵ
		double total = Double.parseDouble(req.getParameter("total"));
		/**
		 * 2������service��loadCartItems��������List<CartItem>
		 */
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/**
		 * 3��������Ϣ��ת����showCart.jsp
		 */
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);
		return "/jsps/cart/showitem.jsp";
	}
	/**
	 * ���¹��ﳵ����Ŀ����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//��Ҫ�޸ĵ�Ŀ��
		String cartItemId = req.getParameter("cartItemId");
		//��Ҫ�޸ĵ�����
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		//����service�������и�����Ŀ����
		CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
		/**
		 * ���ͻ��˷���json����
		 */
		StringBuilder sb = new StringBuilder("{");
		//ע�⣬append(":");ð�Ÿ�����Ҫ���հף���Ȼ����Ч���������Ķ�ͷ�ۡ�
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		resp.getWriter().print(sb);
		return null;
	}
	
	/**
	 * ����ɾ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����ȡcartItemIds����
		 * 2������service��bathDelete����
		 * 3������list.jsp
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds);
		return myCart(req,resp);
	}
	
	/**
	 * ��ӹ��ﳵ��Ŀ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����װ�����ݵ�CartItem��(bid,quantity)
		 */
		Map map = req.getParameterMap();
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map,Book.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		cartItem.setBook(book);
		cartItem.setUser(user);
		
		/**
		 * 2������service���������Ŀ
		 */
		cartItemService.add(cartItem);
		/**
		 * 3����ѯ����ǰ�û�������Ŀ��ת����list.jsp��ʾ
		 */
		return myCart(req,resp);
	}
	
	/**
	 * �ҵĹ��ﳵ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1���õ�uid
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		/**
		 * 2��ͨ��service�õ���ǰ�û��Ĺ��ﳵ��Ŀ
		 */
		List<CartItem> cartItemList = cartItemService.myCart(uid);
		/**
		 * 3������������ת����/cart/list.jsp
		 */
		req.setAttribute("cartItemList",cartItemList);
		return "/jsps/cart/list.jsp";
	}
}
