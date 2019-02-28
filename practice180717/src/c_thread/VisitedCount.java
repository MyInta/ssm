package c_thread;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitedCount extends HttpServlet{
	
	int count=1;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//游览器解析数据字符集
		resp.setContentType("text/html;charset=utf-8");
		//服务器发送数据字符集
		resp.setCharacterEncoding("utf-8");

		synchronized(this){
			resp.getWriter().write("你是第"+count+"个访问者");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		ServletConfig con = this.getServletConfig();
		Enumeration<String> enu = con.getInitParameterNames();
		while(enu.hasMoreElements()) {
			String name = enu.nextElement();
			System.out.println(name+":"+con.getInitParameter(name));
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
