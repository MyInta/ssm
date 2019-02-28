package cn.ssijri.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIOC {
	
	@Test
	public void testUser() {
		//1������spring�����ļ��������ļ���������
		ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
		//2���õ����ô����Ķ���
		User user = (User) context.getBean("user");
		System.out.println(user);
		user.add();
	}
}