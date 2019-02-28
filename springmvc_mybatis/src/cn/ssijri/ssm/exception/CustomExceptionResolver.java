package cn.ssijri.ssm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 * 
 * 1、判断是否系统自定义异常
 * 2、是，直接取出异常；否，构造自定义异常
 * @author 银涛
 *
 */
public class CustomExceptionResolver implements HandlerExceptionResolver{
	
	
	/**
	 * ex 系统抛出异常
	 * 
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler,Exception ex) {
		//handler就是处理器适配器需要执行的Handler对象（只有method）
		//解析异常类型
		//如果该异常是系统自定义的异常，直接去除异常值。在错误页面显示
		/*String message = null;
		if(ex instanceof CustomException) {
			message = ((CustomException)ex).getMessage();
		}else {
			//若非，构造一个自定义异常（信息为“未知错误”）
			message="未知错误";
		}*/
		
		//上面代码转化为
		CustomException customException = null;
		if(ex instanceof CustomException) {
			//如果是系统自定义异常
			customException = (CustomException)ex;
		}else {
			//若非自定义异常，构造一个异常
			customException = new CustomException("CustomExceptionResolver:错误信息");
		}
		//获取错误信息
		String message = customException.getMessage();
		//错误信息传到页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message",message);
		//指向错误页面
		modelAndView.setViewName("error");
		
		return modelAndView;
	}

}
