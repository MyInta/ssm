package cn.ssijri.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import cn.ssijri.po.Items;

/**
 * 实现HttpRequestHandler接口的处理器
 * @author 银涛
 *
 */
public class ItemsController2 implements HttpRequestHandler{

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		//设置模型数据
		request.setAttribute("itemsList",itemsList);
		//设置视图
		request.getRequestDispatcher("/WEB-INF/jsp/items/itemsList.jsp").forward(request, response);
		
		//使用此方法可以设置响应数据格式，比如响应json数据
		/*response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write("json串");*/
	}

}
