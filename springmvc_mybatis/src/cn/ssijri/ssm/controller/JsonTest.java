package cn.ssijri.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ssijri.ssm.po.ItemsCustom;

/**
 * json测试
 * @author 银涛
 *
 */
@Controller
public class JsonTest {
	
	//请求json数据，返回json（商品信息）
	//@RequestBody 请求的json串
	//@ResponseBody 将itemsCustom转换返回json
	@RequestMapping("/requestJson")
	@ResponseBody 
	public ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) {

		//@ResponseBody 将itemsCustom转换返回json
		return itemsCustom;
	}
	
	//请求key/value，输出json
    @RequestMapping("/responseJson")
    @ResponseBody
    public ItemsCustom responseJson(ItemsCustom itemsCustom) {

        //@ResponseBody将itemsCustom转成json输出
        return itemsCustom;
    }

}
