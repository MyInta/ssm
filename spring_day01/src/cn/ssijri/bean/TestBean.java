package cn.ssijri.bean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {
	
	@Test
	public void testBean() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
//		Bean2 bean2 = (Bean2) context.getBean("bean2");
//		System.out.println(bean2);
//		bean2.add();
		Bean3 bean3 = (Bean3) context.getBean("bean3");
		System.out.println(bean3);
		bean3.add();
	}
	
}
