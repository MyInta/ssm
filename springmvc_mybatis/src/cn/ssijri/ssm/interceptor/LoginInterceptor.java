package cn.ssijri.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ��¼��֤��������
 * @author inta
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	//����Handler����ִ��ǰ
	//���� ���������֤�������Ȩ
	//���������֤ ��ʧ����Ҫ���ز�����ִ��
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//��������url
		String url = request.getRequestURI();
		//�ж�url�Ƿ��ǹ�����ַ��ʵ��ʹ��ʱ��������ַ�����ļ��У�
		//���﹫����ַ�ǵ�½�ύ�ĵ�ַ
		if(url.indexOf("login.action")>=0) {
			//���Ҫ��¼�ύ������
			return true;
		}
		
		//�ж�session
		HttpSession session = request.getSession();
		//��session��ȡ���û���Ϣ
		String username = (String) session.getAttribute("username");
		
		if(username!=null) {
			//��ݴ��ڣ�����
			return true;
		}
		
		//ִ�е������ʾ�����֤ʧ�ܣ���Ҫ��תҳ��
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
		return true;
	}

	//����Handler����֮�󣬷���modelAndView֮ǰ
	//Ӧ�ó�����modelAndView�����������õ�ģ�����ݣ���˵�������������ͼ��Ҳ��ͳһָ����ͼ
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("postHandleִ��-----LoginInterceptor");
		
	}

	//ִ��Handler��ɷ���
	//ͳһ���쳣����ͳһ��־
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("afterCompletionִ��-----LoginInterceptor");
	}

}
