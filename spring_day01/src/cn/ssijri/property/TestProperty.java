package cn.ssijri.property;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestProperty {
	
	@Test
	public void testProperty() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
//		PropertyDemo1 property = (PropertyDemo1) context.getBean("demo1");
//		property.test1();
		
//		PropertyDemo2 property2 = (PropertyDemo2) context.getBean("propertyDemo2");
//		property2.test1();
//		UserService service = (UserService) context.getBean("userService");
//		service.add();
		
		Person p = (Person) context.getBean("person");
		p.test01();
	}
}
