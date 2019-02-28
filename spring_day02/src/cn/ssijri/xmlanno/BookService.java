package cn.ssijri.xmlanno;

import javax.annotation.Resource;

/**
 * 测试配置文件和注解开发结合方式
 * 所以类名上就不需要注解@Component之类了
 * @author 银涛
 *
 */
public class BookService {

	//使用注解注入属性
	//（name中值根据xml找bean的id，而对象本身命名与创建无关）
	@Resource(name="bookDao")
	private BookDao bookDao1;
	
	@Resource(name="orderDao")
	private OrderDao orderDao;
	
	public void add() {
		System.out.println("BookService... ...");
		bookDao1.add();
		orderDao.add();
	}
}
