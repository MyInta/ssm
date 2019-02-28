package cn.ssijri.ssm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登陆页面控制
 * @author 银涛
 *
 */
@Controller
public class LoginController {
	
	//登陆
	@RequestMapping("/login")
	public String login(HttpSession session,String username,String password)throws Exception {
		
		//用service用户验证
		//。。。
		
		//在session保存用户信息
		session.setAttribute("username", username);
		//重定向到商品列表页面
		return "redirect:/items/queryItems.action";
	}
	
	//退出
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		
		//清除session
		session.invalidate();
		//重定向到商品列表页面
		return "redirect:/items/queryItems.action";
	}
	

}
