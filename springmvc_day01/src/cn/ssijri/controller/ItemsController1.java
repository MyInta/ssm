package cn.ssijri.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cn.ssijri.po.Items;

/**
 * 实现controller接口的处理器
 * @author 银涛
 *
 */
public class ItemsController1 implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//调用service查询数据库，查询商品列表，这里使用静态数据模拟
		List<Items> itemsList = new ArrayList<>();
		Items items_1 = new Items();
		items_1.setName("联想");
		items_1.setPrice(5000f);
		items_1.setDetail("2018年最新款联想笔记本电脑！");
		
		Items items_2 = new Items();
		items_2.setName("苹果");
		items_2.setPrice(8000f);
		items_2.setDetail("美国进口最新款苹果笔记本！");
		itemsList.add(items_1);
		itemsList.add(items_2);
		
		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//相当于request的setAttribute，在jsp中通过itemsList获取数据
		modelAndView.addObject("itemsList",itemsList);
		//指定视图
		modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		return modelAndView;
	}

}
