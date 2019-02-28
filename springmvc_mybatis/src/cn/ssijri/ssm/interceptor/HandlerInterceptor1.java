package cn.ssijri.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HandlerInterceptor1 implements HandlerInterceptor{

	//����Handler����ִ��ǰ
	//���� ���������֤�������Ȩ
	//���������֤ ��ʧ����Ҫ���ز�����ִ��
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		
		System.out.println("preHandleִ��-----HandlerInterceptor1");
		//return false ����
		//return true ͨ��
		return true;
	}

	//����Handler����֮�󣬷���modelAndView֮ǰ
	//Ӧ�ó�����modelAndView�����������õ�ģ�����ݣ���˵�������������ͼ��Ҳ��ͳһָ����ͼ
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("postHandleִ��-----HandlerInterceptor1");
	}

	//ִ��Handler��ɷ���
	//ͳһ���쳣����ͳһ��־
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("afterCompletionִ��-----HandlerInterceptor2");
	}
	

}
