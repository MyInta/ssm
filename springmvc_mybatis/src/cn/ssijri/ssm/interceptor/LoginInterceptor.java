package cn.ssijri.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录认证的拦截器
 * @author inta
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	//进入Handler方法执行前
	//场景 用于身份认证、身份授权
	//比如身份认证 若失败需要拦截不向下执行
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//获得请求的url
		String url = request.getRequestURI();
		//判断url是否是公开地址（实际使用时将公开地址配置文件中）
		//这里公开地址是登陆提交的地址
		if(url.indexOf("login.action")>=0) {
			//如果要登录提交，放行
			return true;
		}
		
		//判断session
		HttpSession session = request.getSession();
		//从session中取出用户信息
		String username = (String) session.getAttribute("username");
		
		if(username!=null) {
			//身份存在，放行
			return true;
		}
		
		//执行到这里表示身份验证失败，需要跳转页面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
		return true;
	}

	//进入Handler方法之后，返回modelAndView之前
	//应用场景从modelAndView出发：将公用的模型数据（如菜单导航）传到视图，也能统一指定视图
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("postHandle执行-----LoginInterceptor");
		
	}

	//执行Handler完成方法
	//统一的异常处理，统一日志
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("afterCompletion执行-----LoginInterceptor");
	}

}
