package cn.itcast.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.cart.dao.CartItemDao;
import cn.itcast.goods.cart.domain.CartItem;

public class CartItemService {

	private CartItemDao cartItemDao = new CartItemDao();
	
	/**
	 * ���ض��CartItem��Ŀ
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
	 * �޸Ĺ��ﳵ��Ŀ����
	 * ͨ��id��ù��ﳵ��Ϣ�����
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId,int quantity) {
		try {
			//�ȸ��¹��ﳵ����Ŀ����
			cartItemDao.updateQuantity(cartItemId, quantity);
			//Ȼ�󷵻ظ��º�Ĺ��ﳵ��Ŀ��Ϣ
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ����ɾ��
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
	 * �����Ŀ
	 * @param cartItem
	 */
	public void add(CartItem cartItem) {
		/**
		 * 1��ʹ��uid��bid��ѯ��Ŀ
		 */
		try {
			CartItem _cartItem = cartItemDao.findByUidAndBid(
					cartItem.getUser().getUid(), cartItem.getBook().getBid());
			if(null==_cartItem) {//���ԭ��û����Ŀ�������Ŀ
				//���ø�����һ��uuid
				cartItem.setCartItemId(CommonUtils.uuid());
				//�����Ŀ
				cartItemDao.addCartItem(cartItem);
			}else {//���ԭ���У����޸���Ŀ����
				int quantity = cartItem.getQuantity()+_cartItem.getQuantity();
				//���������ݼ��������������������
				cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * �ҵĹ��ﳵ����
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
