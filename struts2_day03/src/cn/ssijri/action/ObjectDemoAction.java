package cn.ssijri.action;

import com.opensymphony.xwork2.ActionSupport;

import cn.ssijri.entity.User;
/**
 * ��ֵջ�м������
 * @author ����
 *
 */
public class ObjectDemoAction extends ActionSupport {
	
	//user�Ķ��󴴽����Էŵ�execute��ȥ������Ӱ��
	User user = new User();
	//get���� User bean�в���Ҫget����
	public User getUser() {
		return user;
	}
	
	@Override
	public String execute() throws Exception {
		user.setName("Inta");
		user.setPassword("123");
		user.setAddress("����");
		return "success";
	}
}
