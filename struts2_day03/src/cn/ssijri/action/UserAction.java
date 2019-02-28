package cn.ssijri.action;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 用于验证创建action时会重复创建多个对象，而一个值栈中只有一个action
 * @author 银涛
 *
 */
public class UserAction extends ActionSupport {

	public UserAction() {
		System.out.println("创建Action... ...");
	}
	
	@Override
	public String execute() throws Exception {
		return NONE;
	}
}
