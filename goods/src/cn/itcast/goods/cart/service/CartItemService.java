package cn.itcast.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.cart.dao.CartItemDao;
import cn.itcast.goods.cart.domain.CartItem;

public class CartItemService {

	private CartItemDao cartItemDao = new CartItemDao();
	
	/**
	 * 加载多个CartItem条目
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds){
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
	}
	
	/**
	 * 修改购物车条目数量
	 * 通过id获得购物车信息后更新
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId,int quantity) {
		try {
			//先更新购物车内条目数量
			cartItemDao.updateQuantity(cartItemId, quantity);
			//然后返回更新后的购物车条目信息
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 批量删除
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加条目
	 * @param cartItem
	 */
	public void add(CartItem cartItem) {
		/**
		 * 1、使用uid和bid查询条目
		 */
		try {
			CartItem _cartItem = cartItemDao.findByUidAndBid(
					cartItem.getUser().getUid(), cartItem.getBook().getBid());
			if(null==_cartItem) {//如果原来没有条目，添加条目
				//设置给参数一个uuid
				cartItem.setCartItemId(CommonUtils.uuid());
				//添加条目
				cartItemDao.addCartItem(cartItem);
			}else {//如果原来有，则修改条目数量
				int quantity = cartItem.getQuantity()+_cartItem.getQuantity();
				//将新老数据加起来后重新设给老数据
				cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 我的购物车功能
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid){
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
