package cn.ssijri.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.ssijri.entity.User;

public class Form2DemoAction extends ActionSupport{
	private User user;
	
	@Override
	public String execute() throws Exception {
		//�ڶ��ַ�ʽ��ʹ��ServletActionContext���ȡ
		//1����ȡrequest����
		HttpServletRequest request = ServletActionContext.getRequest();
		//2������request�����õ������
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
