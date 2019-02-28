package cn.ssijri.action;

import com.opensymphony.xwork2.ActionSupport;

import cn.ssijri.entity.User;
/**
 * 往值栈中加入对象
 * @author 银涛
 *
 */
public class ObjectDemoAction extends ActionSupport {
	
	//user的对象创建可以放到execute中去，都不影响
	User user = new User();
	//get方法 User bean中不需要get方法
	public User getUser() {
		return user;
	}
	
	@Override
	public String execute() throws Exception {
		user.setName("Inta");
		user.setPassword("123");
		user.setAddress("广州");
		return "success";
	}
}
