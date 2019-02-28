package cn.ssijri.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ssijri.ssm.po.ItemsCustom;

/**
 * json����
 * @author ����
 *
 */
@Controller
public class JsonTest {
	
	//����json���ݣ�����json����Ʒ��Ϣ��
	//@RequestBody �����json��
	//@ResponseBody ��itemsCustomת������json
	@RequestMapping("/requestJson")
	@ResponseBody 
	public ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) {

		//@ResponseBody ��itemsCustomת������json
		return itemsCustom;
	}
	
	//����key/value�����json
    @RequestMapping("/responseJson")
    @ResponseBody
    public ItemsCustom responseJson(ItemsCustom itemsCustom) {

        //@ResponseBody��itemsCustomת��json���
        return itemsCustom;
    }

}
