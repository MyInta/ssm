package cn.ssijri.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {
	
	@Test
	public void testAop() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
		Book book = (Book) context.getBean("book");
		book.add();
	}
}
