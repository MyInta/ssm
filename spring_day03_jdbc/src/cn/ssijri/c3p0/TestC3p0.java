package cn.ssijri.c3p0;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestC3p0 {
	
	@Test
	public void testC3p0() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
		UserService userService = (UserService) context.getBean("userService");
		userService.add();
	}
}
