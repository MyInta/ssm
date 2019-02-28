package goods.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import goods.book.domain.Book;
import goods.order.domain.Order;
import goods.order.domain.OrderItem;
import goods.pager.Expression;
import goods.pager.PageBean;
import goods.pager.PageContants;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {

	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 * @throws SQLException 
	 */
	public int findStatus(String oid) throws SQLException {
		String sql = "select status from t_order where oid=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(),oid);
		return number.intValue();
	}
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @throws SQLException 
	 */
	public void updateStatus(String oid,int status) throws SQLException {
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql,status,oid);
	}
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		//order中会缺少当前用户，owner但不影响
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		//为当前订单添加订单条目
		LoadOrderItem(order);
		return order;
	}
	
	/**
	 * 生成订单
	 * @param order
	 * @throws SQLException 
	 */
	public void add(Order order) throws SQLException {
		/**
		 * 1、插入订单
		 */
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getStatus(),order.getAddress(),order.getOwner().getUid()};
		qr.update(sql,params);
		
		/**
		 * 2、循环遍历订单所有条目，让每个条目生成一个Object数组
		 * 多个条目就是对应Object[][]
		 */
		sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] objs = new Object[len][];
		for(int i=0;i<len;i++) {
			OrderItem item = order.getOrderItemList().get(i);
			objs[i] = new Object[] {
					item.getOrderItemId(),
					item.getQuantity(),
					item.getSubtotal(),
					item.getBook().getBid(),
					item.getBook().getBname(),
					item.getBook().getCurrPrice(),
					item.getBook().getImage_b(),
					item.getOrder().getOid()
					};
		}
		qr.batch(sql, objs);
	}
	
	/**
	 * 按用户查询订单
	 * @param uid 用户id
	 * @param pc 当前页码
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid","=",uid));
		return findByCriteria(exprList,pc);
	}
	/**
	 * 查询所有
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findAll(int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteria(exprList,pc);
	}
	
	/**
	 * 按状态查询
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByStatus(int status,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=",status+""));//加“”是因为参数要求是字符串形式 
		return findByCriteria(exprList,pc);
	}
	
	/**
	 * 通用的查询方法
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	private PageBean<Order> findByCriteria(List<Expression> exprList,int pc) throws SQLException{
		/**
		 * 1、得到ps
		 * 2、得到tr
		 * 3、得到beanList
		 * 4、创建PageBean，返回
		 */
		/**
		 * 1、得到ps
		 */
		int ps = PageContants.ORDER_PAGE_SIZE;
		/**
		 * 2、通过exprList来生成where子句
		 * 
		 */
		StringBuilder whereSql =new StringBuilder(" where 1=1 ");
		List<Object> params = new ArrayList<Object>();//sql语句中对应占位符的值
		for(Expression expr:exprList) {
			//添加条件以and开头，条件的名称、运算符（其中运算符如果为is null则无值）
			whereSql.append(" and ").append(expr.getName()).append(" ")
			.append(expr.getOperator()).append(" ");
			// where 1=1 and bid = 
			if(!expr.getOperator().equals("is null")) {//如果值非空
				//运算符为is null追加问好，params添加对应值
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		/**
		 * 3、得到总记录数tr
		 */
		String sql = "select count(*) from t_order"+whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();//得到总记录数
		
		/**
		 * 4、获得beanList,即当前页记录 其中sql语句的orderBy是一个字段
		 */
		sql = "select * from t_order"+whereSql+" order by ordertime desc limit ?,?";
		params.add((pc-1)*ps);//第一个问号,当前页编号减一乘以页面记录数量，获得最终结果为首行记录下标
		params.add(ps);//第二个问号.一共查询几行，即每页记录数
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class),params.toArray());
		//虽然获得了订单列表，但是没有获得订单条目
		//需要遍历每个订单，添加订单条目
		for(Order order:beanList) {
			LoadOrderItem(order);
		}
		/**
		 * 5、创建PageBean，设置参数
		 */
		PageBean<Order> pb = new PageBean<Order>();
		//其中pageBean 没有url 由servlet实现
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		return pb;
	}
	
	/**
	 * 为指定的Order加载所有OrderItem
	 * @param order
	 * @throws SQLException
	 */
	private void LoadOrderItem(Order order) throws SQLException {
		/*
		 * 1、给出sql语句 select * from t_orderitem where oid=?
		 * 2、执行之，得到List<OrderItem>
		 * 3、设置给Order对象
		 */
		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		//将orderItemList条目设置到订单表中
		order.setOrderItemList(orderItemList);
	}
	
	/**
	 * 把多个Map转换为OrderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map:mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}
	/**
	 * 将一个Map转成OrderItem
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}
}
