package cn.ssijri.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestTx {

	@Test
	public void testTx() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean3anno.xml");//bean3anno.xml(ע����ʽ��)
		UserService userService = (UserService) context.getBean("userService");
		userService.account();
	}
}
