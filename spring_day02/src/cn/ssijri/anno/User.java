package cn.ssijri.anno;

import org.springframework.stereotype.Component;

@Component(value="user")	//�൱�������ļ���ʽ��<bean id="user" class="xxxx"></bean>
public class User {
	private User() {
		//������ôŪ��һ�����鲢��������ɻ�һ������˽�л�����ɨ��ʱ��Ĵ�����˳������
		System.out.println("cn.ssijri.anno.User�Ŀչ�����");
	}
	public void add() {
		System.out.println("ע����ʽ������");
	}
}
