package cn.ssijri.action;

import com.opensymphony.xwork2.ActionSupport;
/**
 * ������֤����actionʱ���ظ�����������󣬶�һ��ֵջ��ֻ��һ��action
 * @author ����
 *
 */
public class UserAction extends ActionSupport {

	public UserAction() {
		System.out.println("����Action... ...");
	}
	
	@Override
	public String execute() throws Exception {
		return NONE;
	}
}
