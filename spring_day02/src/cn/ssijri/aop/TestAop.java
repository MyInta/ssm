package cn.ssijri.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {
	
	@Test
	public void testAop() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
		MyTarget target = (MyTarget) context.getBean("myTarget");
		target.add();
	}
}
