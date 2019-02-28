package cn.ssijri.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ssijri.po.Items;

/**
 * ʵ��controller�ӿڵĴ�����
 * @author ����
 *
 */
@Controller
public class ItemsController3 {
	//��Ʒ��ѯ
	//@RequestMappingʵ�ֶ�queryItems������urlӳ�䣬һ��������Ӧһ��url
	//���齫url�ͷ�����дһ��������ά��
	@RequestMapping("/queryItems")
	public ModelAndView queryItems()throws Exception{
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
		//�±ߵ�·�����������ͼ������������jsp��·��ǰ׺�ͺ�׺���޸�Ϊitems/itemsList
		//modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
        //�±ߵ�·�����þͿ��Բ��ڳ�����ָ��jsp·����ǰ׺�ͺ�׺
        modelAndView.setViewName("items/itemsList");
		return modelAndView;
	}
	
	//���巽��
	

}
