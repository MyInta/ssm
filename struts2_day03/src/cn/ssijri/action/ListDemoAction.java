package cn.ssijri.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import cn.ssijri.entity.User;
/**
 * 向值栈中放入list
 * @author 银涛
 *
 */
public class ListDemoAction extends ActionSupport{
	//新建list
	List<User> list = new ArrayList<User>();
	//get方法 事实证明，list内属性User没有get方法也行
	public List getList() {
		return list;
	}
	
	@Override
	public String execute() throws Exception {
		User user1 = new User();
		user1.setName("小红");
		user1.setPassword("111");
		user1.setAddress("东");
		
		User user2 = new User();
		user2.setName("小蓝");
		user2.setPassword("222");
		user2.setAddress("西");
		
		list.add(user1);
		list.add(user2);
		
		return "success";
	}
}
