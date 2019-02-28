package cn.ssijri.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cn.ssijri.po.Items;

/**
 * ʵ��controller�ӿڵĴ�����
 * @author ����
 *
 */
public class ItemsController1 implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		//����ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//�൱��request��setAttribute����jsp��ͨ��itemsList��ȡ����
		modelAndView.addObject("itemsList",itemsList);
		//ָ����ͼ
		modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		return modelAndView;
	}

}
