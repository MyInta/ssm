package goods.order.service;

import java.sql.SQLException;

import goods.order.dao.OrderDao;
import goods.order.domain.Order;
import goods.pager.PageBean;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {

	private OrderDao orderDao = new OrderDao();
	
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 */
	public void updateStatus(String oid,int status) {
		try {
			orderDao.updateStatus(oid,status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			//添加事务
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			//提交事务
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	/**
	 * 生成订单
	 * @param order
	 */
	public void createOrder(Order order){
		try {
			//添加事务
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			//提交事务
			JdbcUtils.commitTransaction();

		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> myOrders(String uid,int pc){
		try {
			//添加事务
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			//提交事务
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	/**
	 * 查询所有
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findAll(int pc){
		try {
			//添加事务
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findAll( pc);
			//提交事务
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按照状态查询
	 * @param status
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findByStatus(int status,int pc){
		try {
			//添加事务
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByStatus(status, pc);
			//提交事务
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
}
