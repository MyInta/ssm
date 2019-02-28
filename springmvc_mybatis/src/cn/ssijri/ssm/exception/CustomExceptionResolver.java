package cn.ssijri.ssm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * ȫ���쳣������
 * 
 * 1���ж��Ƿ�ϵͳ�Զ����쳣
 * 2���ǣ�ֱ��ȡ���쳣���񣬹����Զ����쳣
 * @author ����
 *
 */
public class CustomExceptionResolver implements HandlerExceptionResolver{
	
	
	/**
	 * ex ϵͳ�׳��쳣
	 * 
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler,Exception ex) {
		//handler���Ǵ�������������Ҫִ�е�Handler����ֻ��method��
		//�����쳣����
		//������쳣��ϵͳ�Զ�����쳣��ֱ��ȥ���쳣ֵ���ڴ���ҳ����ʾ
		/*String message = null;
		if(ex instanceof CustomException) {
			message = ((CustomException)ex).getMessage();
		}else {
			//���ǣ�����һ���Զ����쳣����ϢΪ��δ֪���󡱣�
			message="δ֪����";
		}*/
		
		//�������ת��Ϊ
		CustomException customException = null;
		if(ex instanceof CustomException) {
			//�����ϵͳ�Զ����쳣
			customException = (CustomException)ex;
		}else {
			//�����Զ����쳣������һ���쳣
			customException = new CustomException("CustomExceptionResolver:������Ϣ");
		}
		//��ȡ������Ϣ
		String message = customException.getMessage();
		//������Ϣ����ҳ��
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message",message);
		//ָ�����ҳ��
		modelAndView.setViewName("error");
		
		return modelAndView;
	}

}
