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
	 * 生成where子句
	 * @param len 参数数量
	 * @return where子句
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
	 * 通过查询id加载多个CartItem条目列表
	 * @param cartItemIds 条目id
	 * @return
	 * @throws SQLException 
	 */
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException{
		/**
		 * 1、将cartItemIds包装成Object[]数组
		 */
		Object[] cartItemArray = cartItemIds.split(",");
		/**
		 * 2、生成where子句
		 */
		String whereSql = toWhereSql(cartItemArray.length);
		/**
		 * 3、生成sql语句
		 */
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and "+whereSql;
		/**
		 * 4、执行sql返回List
		 */
		return toCartItemList(qr.query(sql, new MapListHandler(),cartItemArray));
	}
	/**
	 * 通过购物车条目id获得购物车信息
	 * @param cartItemId 购物车条目的id
	 * @return 购物车信息
	 * @throws SQLException
	 */
	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql,new MapHandler(),cartItemId);
		return toCartItem(map);
	}
	/**
	 * 按照id批量删除
	 * @param cartItemIds
	 * @throws SQLException 
	 */
	public void batchDelete(String cartItemIds) throws SQLException {
		/**
		 * 1、把cartItemId转换成数组
		 * 		把cartItemIds转换成where子句
		 * 		与delete from连接在一起后执行
		 */
		Object[] cartItemArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemArray.length);
		String sql = "delete from t_cartitem where "+whereSql;
		qr.update(sql,cartItemArray); //其中cartItemArray必需是Object类型数组
	}
	
	/**
	 * 按用户编号和图书编号查看购物车信息
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
	 * 修改指定条目的数量
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException 
	 */
	public void updateQuantity(String cartItemId,int quantity) throws SQLException {
		String sql = "update t_cartitem set quantity=? where cartItemId=?";
		qr.update(sql,quantity,cartItemId);
	}
	/**
	 * 添加CartItem条目
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
	 * 把一个map映射成一个cartItem
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
	 * 把多个map<List<Map>>映射成多个CartItem(List<CartItem>)
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
	 * 按用户查询购物车条目
	 * 多表查询，获得页面需要的图书信息和购物车信息
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
