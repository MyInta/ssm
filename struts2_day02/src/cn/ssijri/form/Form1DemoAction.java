package cn.ssijri.form;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Form1DemoAction extends ActionSupport{
	
	@Override
	public String execute() throws Exception {
		//��һ�ַ�ʽ��ʹ��ActionContext���ȡ
		//1����ȡActionContext��
		ActionContext context = ActionContext.getContext();
		//2�����÷�����ñ�����
		//keyΪ��nameֵ��valueΪ����ֵ
		Map<String,Object> map = context.getParameters();
		
		Set<String> keys = map.keySet();
		for(String key:keys) {
			//����key�õ�valueֵ
			//֮������������ʽ����Ϊ���ܴ��ڸ�ѡ��
			Object[] obj = (Object[]) map.get(key);
			System.out.println(Arrays.toString(obj));
		}
		
		return NONE;
	}
}
