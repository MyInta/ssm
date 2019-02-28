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
		 * 1、找到参数cartItemIds
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		//传递总计值
		double total = Double.parseDouble(req.getParameter("total"));
		/**
		 * 2、调用service的loadCartItems方法返回List<CartItem>
		 */
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/**
		 * 3、保存信息并转发到showCart.jsp
		 */
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);
		return "/jsps/cart/showitem.jsp";
	}
	/**
	 * 更新购物车内条目数量
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//需要修改的目标
		String cartItemId = req.getParameter("cartItemId");
		//需要修改的数量
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		//调用service方法进行更新条目数量
		CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
		/**
		 * 给客户端返回json对象
		 */
		StringBuilder sb = new StringBuilder("{");
		//注意，append(":");冒号附近不要留空白，不然不出效果问题在哪儿头疼。
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		resp.getWriter().print(sb);
		return null;
	}
	
	/**
	 * 批量删除
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、获取cartItemIds参数
		 * 2、调用service的bathDelete方法
		 * 3、返回list.jsp
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds);
		return myCart(req,resp);
	}
	
	/**
	 * 添加购物车条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、封装表单数据到CartItem中(bid,quantity)
		 */
		Map map = req.getParameterMap();
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map,Book.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		cartItem.setBook(book);
		cartItem.setUser(user);
		
		/**
		 * 2、调用service进行添加条目
		 */
		cartItemService.add(cartItem);
		/**
		 * 3、查询出当前用户所有条目，转发到list.jsp显示
		 */
		return myCart(req,resp);
	}
	
	/**
	 * 我的购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1、得到uid
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		/**
		 * 2、通过service得到当前用户的购物车条目
		 */
		List<CartItem> cartItemList = cartItemService.myCart(uid);
		/**
		 * 3、保存起来，转发到/cart/list.jsp
		 */
		req.setAttribute("cartItemList",cartItemList);
		return "/jsps/cart/list.jsp";
	}
}
