package cn.itcast.goods.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.cart.domain.CartItem;
import cn.itcast.goods.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemDao {
	
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * ����where�Ӿ�
	 * @param len ��������
	 * @return where�Ӿ�
	 */
	private String toWhereSql(int len) {
		StringBuilder sb = new StringBuilder("cartItemId in(");
		for(int i=0;i<len;i++) {
			sb.append("?");
			if(i<len-1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * ͨ����ѯid���ض��CartItem��Ŀ�б�
	 * @param cartItemIds ��Ŀid
	 * @return
	 * @throws SQLException 
	 */
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException{
		/**
		 * 1����cartItemIds��װ��Object[]����
		 */
		Object[] cartItemArray = cartItemIds.split(",");
		/**
		 * 2������where�Ӿ�
		 */
		String whereSql = toWhereSql(cartItemArray.length);
		/**
		 * 3������sql���
		 */
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and "+whereSql;
		/**
		 * 4��ִ��sql����List
		 */
		return toCartItemList(qr.query(sql, new MapListHandler(),cartItemArray));
	}
	/**
	 * ͨ�����ﳵ��Ŀid��ù��ﳵ��Ϣ
	 * @param cartItemId ���ﳵ��Ŀ��id
	 * @return ���ﳵ��Ϣ
	 * @throws SQLException
	 */
	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql,new MapHandler(),cartItemId);
		return toCartItem(map);
	}
	/**
	 * ����id����ɾ��
	 * @param cartItemIds
	 * @throws SQLException 
	 */
	public void batchDelete(String cartItemIds) throws SQLException {
		/**
		 * 1����cartItemIdת��������
		 * 		��cartItemIdsת����where�Ӿ�
		 * 		��delete from������һ���ִ��
		 */
		Object[] cartItemArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemArray.length);
		String sql = "delete from t_cartitem where "+whereSql;
		qr.update(sql,cartItemArray); //����cartItemArray������Object��������
	}
	
	/**
	 * ���û���ź�ͼ���Ų鿴���ﳵ��Ϣ
	 * @param uid
	 * @param bid
	 * @return
	 * @throws SQLException 
	 */
	public CartItem findByUidAndBid(String uid,String bid) throws SQLException {
		String sql = "select * from t_cartitem where uid=? and bid=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(),uid,bid);
		CartItem cartItem = toCartItem(map);
		return cartItem;
	}
	/**
	 * �޸�ָ����Ŀ������
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException 
	 */
	public void updateQuantity(String cartItemId,int quantity) throws SQLException {
		String sql = "update t_cartitem set quantity=? where cartItemId=?";
		qr.update(sql,quantity,cartItemId);
	}
	/**
	 * ���CartItem��Ŀ
	 * @param cartItem
	 * @throws SQLException 
	 */
	public void addCartItem(CartItem cartItem) throws SQLException {
		String sql = "insert into t_cartitem(cartItemId,quantity,bid,uid) value(?,?,?,?)";
		Object[] params = {cartItem.getCartItemId(),cartItem.getQuantity(),
				cartItem.getBook().getBid(),cartItem.getUser().getUid()};
		qr.update(sql, params);
	}
	/**
	 * ��һ��mapӳ���һ��cartItem
	 * @param map
	 * @return
	 */
	private CartItem toCartItem(Map<String,Object> map) {
		if(null==map||map.size()==0)return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		User user = CommonUtils.toBean(map, User.class);
		cartItem.setBook(book);
		cartItem.setUser(user);
		return cartItem;
	}
	/**
	 * �Ѷ��map<List<Map>>ӳ��ɶ��CartItem(List<CartItem>)
	 * @param mapList
	 * @return
	 */
	private List<CartItem> toCartItemList(List<Map<String,Object>> mapList){
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		for(Map<String,Object> map:mapList) {
			CartItem cartItem = toCartItem(map);
			cartItemList.add(cartItem);
		}
		return cartItemList;
	}
	/**
	 * ���û���ѯ���ﳵ��Ŀ
	 * ����ѯ�����ҳ����Ҫ��ͼ����Ϣ�͹��ﳵ��Ϣ
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public List<CartItem> findByUser(String uid) throws SQLException{
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and uid=? order by c.orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),uid);
		return toCartItemList(mapList);
	}
}
