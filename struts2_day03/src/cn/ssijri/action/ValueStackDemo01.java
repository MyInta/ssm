package cn.ssijri.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * ģ����ֵջ�з�������
 * ����action����get����setֵ��ԭ�����ڽ�ʡ�ռ�
 * ���������context�����ֵ���Ὺ���¿ռ䣩
 * @author ����
 *
 */
public class ValueStackDemo01 extends ActionSupport{
	//�����ַ�ʽ action�����������Լ���Ӧ��get
	private String name;
	
	public String getName() {
		return name;
	}
	
	@Override
	public String execute() throws Exception {
		//��һ�ַ�ʽ set
//		ActionContext context = ActionContext.getContext();
//		ValueStack stack = context.getValueStack();
//		stack.set("key01", "value01");
		//�ڶ��ַ�ʽ push
//		stack.push("string02");
		
		
		//�����ַ�ʽ
		name = "action����set���÷���";
		return "success";
	}
}
