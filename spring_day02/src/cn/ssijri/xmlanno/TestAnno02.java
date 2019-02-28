package cn.ssijri.xmlanno;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnno02 {

	@Test
	public void testAnno() {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("bean2.xml");
		BookService bookService = (BookService) context.getBean("bookService");
		bookService.add();
	}
}
