package cn.ssijri.property;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * ����p����ע��
 * �����ԣ���������Ϊprivate�����򴴽�����������������в��ܵ���
 * 
 * set��ʽע������
 * ���Ը���ע�� arr list map properties����
 * @author ����
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