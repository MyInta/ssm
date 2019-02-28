package cn.itcast.goods.order.domain;

import java.util.List;

import cn.itcast.goods.user.domain.User;

public class Order {

	private String oid;//主键
	private String ordertime;//下单时间
	private double total;//总计
	//订单状态：1未付款 2已付款未发货 3已发货未确认收货 4确认收货交易结算 5取消（前提未付款）
	private int status;
	private String address;//收货地址
	private User owner;//订单的所有者
	private List<OrderItem> orderItemList;
	
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
}
