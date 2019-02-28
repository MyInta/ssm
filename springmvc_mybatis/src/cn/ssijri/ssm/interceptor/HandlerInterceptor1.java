package cn.ssijri.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HandlerInterceptor1 implements HandlerInterceptor{

	//进入Handler方法执行前
	//场景 用于身份认证、身份授权
	//比如身份认证 若失败需要拦截不向下执行
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		
		System.out.println("preHandle执行-----HandlerInterceptor1");
		//return false 拦截
		//return true 通过
		return true;
	}

	//进入Handler方法之后，返回modelAndView之前
	//应用场景从modelAndView出发：将公用的模型数据（如菜单导航）传到视图，也能统一指定视图
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("postHandle执行-----HandlerInterceptor1");
	}

	//执行Handler完成方法
	//统一的异常处理，统一日志
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("afterCompletion执行-----HandlerInterceptor2");
	}
	

}
