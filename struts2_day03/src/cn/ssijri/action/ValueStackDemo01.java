package cn.ssijri.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * 模拟向值栈中放入数据
 * 常用action属性get方法set值的原因在于节省空间
 * （其他获得context后的设值都会开辟新空间）
 * @author 银涛
 *
 */
public class ValueStackDemo01 extends ActionSupport{
	//第三种方式 action内设置属性以及对应的get
	private String name;
	
	public String getName() {
		return name;
	}
	
	@Override
	public String execute() throws Exception {
		//第一种方式 set
//		ActionContext context = ActionContext.getContext();
//		ValueStack stack = context.getValueStack();
//		stack.set("key01", "value01");
		//第二种方式 push
//		stack.push("string02");
		
		
		//第三种方式
		name = "action属性set常用方法";
		return "success";
	}
}
