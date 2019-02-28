package cn.ssijri.property;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 测试p名称注入
 * 经测试，方法不能为private，否则创建对象后在其他的类中不能调用
 * 
 * set方式注入属性
 * 测试复杂注入 arr list map properties属性
 * @author 银涛
 *
 */
public class Person {
	
	private String pname;
	
	private String[] arrs;
	private List<String> list;
	private Map<String,String> map;
	private Properties properties;
	
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	
	public void setArrs(String[] arrs) {
		this.arrs = arrs;
	}


	public void setList(List<String> list) {
		this.list = list;
	}


	public void setMap(Map<String, String> map) {
		this.map = map;
	}


	public void setProperties(Properties properties) {
		this.properties = properties;
	}


	public void test01() {
		System.out.println("pname"+pname);
		System.out.println("arrs"+Arrays.toString(arrs));
		System.out.println("list"+list);
		System.out.println("map"+map);
		System.out.println("properties"+properties);
	}
}
