package cn.ssijri.anno;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnno {
	
	@Test
	public void testAnno() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
//		User user = (User) context.getBean("user");
//		System.out.println(user);
//		user.add();
		UserService userService = (UserService) context.getBean("userService");
		userService.add();
	}
}
