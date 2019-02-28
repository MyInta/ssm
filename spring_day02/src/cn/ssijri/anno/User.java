package cn.ssijri.anno;

import org.springframework.stereotype.Component;

@Component(value="user")	//相当于配置文件方式的<bean id="user" class="xxxx"></bean>
public class User {
	private User() {
		//故意这么弄，一来检验并解决两个疑惑，一构造器私有化、二扫描时候的创建及顺序问题
		System.out.println("cn.ssijri.anno.User的空构造器");
	}
	public void add() {
		System.out.println("注解形式。。。");
	}
}
