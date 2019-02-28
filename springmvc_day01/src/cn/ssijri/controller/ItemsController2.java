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
 * ʵ��HttpRequestHandler�ӿڵĴ�����
 * @author ����
 *
 */
public class ItemsController2 implements HttpRequestHandler{

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//����service��ѯ���ݿ⣬��ѯ��Ʒ�б�����ʹ�þ�̬����ģ��
		List<Items> itemsList = new ArrayList<>();
		Items items_1 = new Items();
		items_1.setName("����");
		items_1.setPrice(5000f);
		items_1.setDetail("2018�����¿�����ʼǱ����ԣ�");
		
		Items items_2 = new Items();
		items_2.setName("ƻ��");
		items_2.setPrice(8000f);
		items_2.setDetail("�����������¿�ƻ���ʼǱ���");
		itemsList.add(items_1);
		itemsList.add(items_2);
		//����ģ������
		request.setAttribute("itemsList",itemsList);
		//������ͼ
		request.getRequestDispatcher("/WEB-INF/jsp/items/itemsList.jsp").forward(request, response);
		
		//ʹ�ô˷�������������Ӧ���ݸ�ʽ��������Ӧjson����
		/*response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write("json��");*/
	}

}
