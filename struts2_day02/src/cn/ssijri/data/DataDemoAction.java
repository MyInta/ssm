package cn.ssijri.data;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.ssijri.entity.User;
/**
 * ģ��������װ ����ϳ���
 * ���Է�װ���Ǽ򵥵ļӸ������Լ�set����
 * @author ����
 *
 */
public class DataDemoAction extends ActionSupport implements ModelDriven<User>{
	private User user = new User();
	@Override
	public User getModel() {
		return user;
	}
	
	@Override
	public String execute() throws Exception {
		System.out.println(user);
		return NONE;
	}

}
