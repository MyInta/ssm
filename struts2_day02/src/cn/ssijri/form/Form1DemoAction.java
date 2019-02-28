package cn.ssijri.form;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Form1DemoAction extends ActionSupport{
	
	@Override
	public String execute() throws Exception {
		//第一种方式，使用ActionContext类获取
		//1、获取ActionContext类
		ActionContext context = ActionContext.getContext();
		//2、调用方法获得表单数据
		//key为表单name值，value为表单数值
		Map<String,Object> map = context.getParameters();
		
		Set<String> keys = map.keySet();
		for(String key:keys) {
			//根据key得到value值
			//之所以是数组形式是因为可能存在复选框
			Object[] obj = (Object[]) map.get(key);
			System.out.println(Arrays.toString(obj));
		}
		
		return NONE;
	}
}
