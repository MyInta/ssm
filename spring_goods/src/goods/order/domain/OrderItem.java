package goods.order.domain;

import goods.book.domain.Book;

public class OrderItem {

	private String orderItemId;//���� 
	private int quantity;//����
	private double subtotal;//С��
/*	private String bid;
	private String bname;
	private double currPrice;
	private String image_b;*/
	private Book book;//��������book
	private Order order;//�����Ķ���
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}