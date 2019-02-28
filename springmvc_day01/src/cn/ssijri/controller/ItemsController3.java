package cn.ssijri.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ssijri.po.Items;

/**
 * 实现controller接口的处理器
 * @author 银涛
 *
 */
@Controller
public class ItemsController3 {
	//商品查询
	//@RequestMapping实现对queryItems方法和url映射，一个方法对应一个url
	//建议将url和方法名写一样，便于维护
	@RequestMapping("/queryItems")
	public ModelAndView queryItems()throws Exception{
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
		//下边的路径，如果在视图解析器中配置jsp的路径前缀和后缀，修改为items/itemsList
		//modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
        //下边的路径配置就可以不在程序中指定jsp路径的前缀和后缀
        modelAndView.setViewName("items/itemsList");
		return modelAndView;
	}
	
	//定义方法
	

}
