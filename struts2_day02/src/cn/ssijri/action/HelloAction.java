package cn.ssijri.action;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport{
	
	@Override
	public String execute() {
		System.out.println("ok");
		return "ok";
	}
}
