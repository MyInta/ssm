package cn.ssijri.data;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.ssijri.entity.User;
/**
 * 模型驱动分装 这个较常用
 * 属性封装就是简单的加个属性以及set即可
 * @author 银涛
 *
 */
public class DataDemoAction extends ActionSupport implements ModelDriven<User>{
	private User user = new User();
	@Override
	public User getModel() {
		return user;
	}
	
	@Override
	public String execute() throws Exception {
		System.out.println(user);
		return NONE;
	}

}
