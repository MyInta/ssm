package cn.ssijri.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import cn.ssijri.entity.User;
/**
 * ��ֵջ�з���list
 * @author ����
 *
 */
public class ListDemoAction extends ActionSupport{
	//�½�list
	List<User> list = new ArrayList<User>();
	//get���� ��ʵ֤����list������Userû��get����Ҳ��
	public List getList() {
		return list;
	}
	
	@Override
	public String execute() throws Exception {
		User user1 = new User();
		user1.setName("С��");
		user1.setPassword("111");
		user1.setAddress("��");
		
		User user2 = new User();
		user2.setName("С��");
		user2.setPassword("222");
		user2.setAddress("��");
		
		list.add(user1);
		list.add(user2);
		
		return "success";
	}
}
