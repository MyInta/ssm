package cn.itcast.goods.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
public class LoginFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/**
		 * 1.获取session中的user
		 * 2.判断是否为null
		 * 	>null 就保存错误信息，转发为msg.jsp
		 *  >不为null 放行
		 */
		HttpServletRequest req = (HttpServletRequest) request;
		Object user = req.getSession().getAttribute("sessionUser");
		if(null==user) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "您处于未登陆状态，请重新登陆");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);
		}else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
