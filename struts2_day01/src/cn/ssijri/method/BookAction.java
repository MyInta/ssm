package cn.ssijri.method;

import com.opensymphony.xwork2.ActionSupport;

public class BookAction extends ActionSupport{

	public String add() {
		System.out.println("add... ...");
		return "none";
	}

	public String update() {
		System.out.println("update... ...");
		return NONE;
	}
}
