package cn.ssijri.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.ssijri.entity.User;

public class Form2DemoAction extends ActionSupport{
	private User user;
	
	@Override
	public String execute() throws Exception {
		//第二种方式，使用ServletActionContext类获取
		//1、获取request对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//2、调用request方法得到结果集
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setAddress(address);
		System.out.println(user.toString());
		return NONE;
	}
}
