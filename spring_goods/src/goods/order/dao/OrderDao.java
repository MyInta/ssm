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
	 * ��ѯ����״̬
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
	 * �޸Ķ���״̬
	 * @param oid
	 * @param status
	 * @throws SQLException 
	 */
	public void updateStatus(String oid,int status) throws SQLException {
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql,status,oid);
	}
	/**
	 * ���ض���
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		//order�л�ȱ�ٵ�ǰ�û���owner����Ӱ��
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		//Ϊ��ǰ������Ӷ�����Ŀ
		LoadOrderItem(order);
		return order;
	}
	
	/**
	 * ���ɶ���
	 * @param order
	 * @throws SQLException 
	 */
	public void add(Order order) throws SQLException {
		/**
		 * 1�����붩��
		 */
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getStatus(),order.getAddress(),order.getOwner().getUid()};
		qr.update(sql,params);
		
		/**
		 * 2��ѭ����������������Ŀ����ÿ����Ŀ����һ��Object����
		 * �����Ŀ���Ƕ�ӦObject[][]
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
	 * ���û���ѯ����
	 * @param uid �û�id
	 * @param pc ��ǰҳ��
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid","=",uid));
		return findByCriteria(exprList,pc);
	}
	/**
	 * ��ѯ����
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findAll(int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteria(exprList,pc);
	}
	
	/**
	 * ��״̬��ѯ
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByStatus(int status,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=",status+""));//�ӡ�������Ϊ����Ҫ�����ַ�����ʽ 
		return findByCriteria(exprList,pc);
	}
	
	/**
	 * ͨ�õĲ�ѯ����
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	private PageBean<Order> findByCriteria(List<Expression> exprList,int pc) throws SQLException{
		/**
		 * 1���õ�ps
		 * 2���õ�tr
		 * 3���õ�beanList
		 * 4������PageBean������
		 */
		/**
		 * 1���õ�ps
		 */
		int ps = PageContants.ORDER_PAGE_SIZE;
		/**
		 * 2��ͨ��exprList������where�Ӿ�
		 * 
		 */
		StringBuilder whereSql =new StringBuilder(" where 1=1 ");
		List<Object> params = new ArrayList<Object>();//sql����ж�Ӧռλ����ֵ
		for(Expression expr:exprList) {
			//���������and��ͷ�����������ơ��������������������Ϊis null����ֵ��
			whereSql.append(" and ").append(expr.getName()).append(" ")
			.append(expr.getOperator()).append(" ");
			// where 1=1 and bid = 
			if(!expr.getOperator().equals("is null")) {//���ֵ�ǿ�
				//�����Ϊis null׷���ʺã�params��Ӷ�Ӧֵ
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		/**
		 * 3���õ��ܼ�¼��tr
		 */
		String sql = "select count(*) from t_order"+whereSql;
		Number number = (Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();//�õ��ܼ�¼��
		
		/**
		 * 4�����beanList,����ǰҳ��¼ ����sql����orderBy��һ���ֶ�
		 */
		sql = "select * from t_order"+whereSql+" order by ordertime desc limit ?,?";
		params.add((pc-1)*ps);//��һ���ʺ�,��ǰҳ��ż�һ����ҳ���¼������������ս��Ϊ���м�¼�±�
		params.add(ps);//�ڶ����ʺ�.һ����ѯ���У���ÿҳ��¼��
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class),params.toArray());
		//��Ȼ����˶����б�����û�л�ö�����Ŀ
		//��Ҫ����ÿ����������Ӷ�����Ŀ
		for(Order order:beanList) {
			LoadOrderItem(order);
		}
		/**
		 * 5������PageBean�����ò���
		 */
		PageBean<Order> pb = new PageBean<Order>();
		//����pageBean û��url ��servletʵ��
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		return pb;
	}
	
	/**
	 * Ϊָ����Order��������OrderItem
	 * @param order
	 * @throws SQLException
	 */
	private void LoadOrderItem(Order order) throws SQLException {
		/*
		 * 1������sql��� select * from t_orderitem where oid=?
		 * 2��ִ��֮���õ�List<OrderItem>
		 * 3�����ø�Order����
		 */
		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		//��orderItemList��Ŀ���õ���������
		order.setOrderItemList(orderItemList);
	}
	
	/**
	 * �Ѷ��Mapת��ΪOrderItem
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
	 * ��һ��Mapת��OrderItem
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
