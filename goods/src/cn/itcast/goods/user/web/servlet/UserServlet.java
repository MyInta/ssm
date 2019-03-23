package cn.itcast.goods.user.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.user.domain.User;
import cn.itcast.goods.user.service.UserService;
import cn.itcast.goods.user.service.exception.UserException;
import cn.itcast.servlet.BaseServlet;

/**
 * 用户模块控制层
 * @author 银涛
 *
 */
public class UserServlet extends BaseServlet {
	
	//往Servlet中注入service属性
	private UserService userService = new UserService();
	/**
	 * 用户名是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateLoginName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1、获得用户名
		 */
		String loginname = req.getParameter("loginname");
		/**
		 * 2、通过service获取校验
		 */
		boolean b = userService.ajaxValidateLoginName(loginname);
		/**
		 * 3、向服务端发送
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * Emial是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1、获得邮箱地址
		 */
		String email = req.getParameter("email");
		/**
		 * 2、通过service获取校验
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/**
		 * 3、向服务端发送
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * 验证码是否正确校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1、获取输入框中校验码
		 */
		String verifyCode = req.getParameter("verifyCode");
		/**
		 * 2、获取图片上真实的验证码
		 */
		String vCode = (String) req.getSession().getAttribute("vCode");
		/**
		 * 3、进行忽略大小写比较，得到结果
		 */
		boolean b = verifyCode.equalsIgnoreCase(vCode);
		/**
		 * 4、发送给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * 注册功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1、封装表单数据
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/**
		 * 2、校验之,传入session参数是为了获得验证码图片真实值
		 */
		Map<String,String> errors = validateRegist(formUser,req.getSession());
		//如果errors map中有内容，说明有错
		if (/* errors!=null&& */errors.size()>0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "/jsps/user/regist.jsp";
		}
		/**
		 * 3、使用service完成业务
		 */
		userService.regist(formUser);
		/**
		 * 4、保存成功信息，转到msg.jsp页面中
		 */
		req.setAttribute("code","success");
		req.setAttribute("msg", "注册成功，请于邮箱点击激活");
		return "/jsps/msg.jsp";
	
	}
	/**
	 * 注册校验，对表单字段逐个校验,当前字段名为key，错误信息为value
	 * @param formUser
	 * @param session
	 * @return
	 */
	private Map<String,String> validateRegist(User formUser,HttpSession session){
		Map<String,String> errors = new HashMap<String,String>();
		/**
		 * 1、校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(null==loginname||loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空");
		}else if(loginname.length()<3||loginname.length()>12){
			errors.put("loginname", "用户名长度要在3-12之间");
		}else if(!userService.ajaxValidateLoginName(loginname)) {
			errors.put("loginname", "用户名已注册");
		}
		/**
		 * 2、校验登陆密码
		 */
		String loginpass = formUser.getLoginpass();
		if(null==loginpass||loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空");
		}else if(loginpass.length()<6||loginpass.length()>12){
			errors.put("loginpass", "密码长度要在3-12之间");
		}
		/**
		 * 3、确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if(null==reloginpass||reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空");
		}else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "二次密码不一致");
		}
		/**
		 * 4、确认邮箱
		 */
		String email = formUser.getEmail();
		if(null==email||email.trim().isEmpty()) {
			errors.put("email", "邮箱不能为空");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "邮箱格式错误");
		}else if(!userService.ajaxValidateEmail(email)) {
			errors.put("email", "邮箱已注册");
		}
		/**
		 * 5、确认校验码
		 */
		String verifyCode = formUser.getVerifyCode();
		String vCode = "";
		if(null!=session.getAttribute("vCode")) {
			vCode = (String) session.getAttribute("vCode");
		}else {
			errors.put("verifycode", "获取验证码图片内容失败！");
			return errors;
		}
		if(null==verifyCode||verifyCode.trim().isEmpty()) {
			errors.put("verifycode", "验证码不能为空");
		}else if(!vCode.equalsIgnoreCase(verifyCode)) {
			errors.put("verifycode", "验证码错误");
		}
		return errors;
	}
	
	/**
	 * 激活功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1、获取激活码
		 * 2、用激活码调用service方法完成激活
		 * 3、如果有异常，保存到request中，转发到msg.jsp
		 * 4、保存成功信息到request中，转发到msg.jsp显示
		 */
		String code = req.getParameter("activationCode");
		try {
			userService.activation(code);
			req.setAttribute("code", "success");//用于msg.jsp内code判断显示叉
			req.setAttribute("msg", "激活成功咯~");
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");//用于msg.jsp内code判断显示叉
		}
		return "/jsps/msg.jsp";
	}
	/**
	 * 用户登陆功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1、封装表单数据到User
		 * 2、校验表单数据
		 * 3、使用service查询User
		 * 4、获得User对象，如果不存在则
		 * 	保存错误信息：用户名或密码错误
		 * 	保存用户信息，为了回显
		 * 	转发到login.jsp
		 * 5、如果存在user查看状态status
		 * 	如果为false则保存错误信息：您未注册
		 * 	保存用户信息，为了回显
		 * 	转发到login.jsp
		 * 6、登陆成功
		 * 	保存当前查询结果的user到session中
		 * 	保存当前用户名称到cookie中，注意编码问题
		 */
		//封装数据
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		//调用userService方法获得数据库内查询后出的user
		User user = userService.login(formUser);
		//校验(validateLogin校验方法中是验证码这一环节)
		Map<String,String> errors = validateLogin(formUser,req.getSession());
		//如果errors map中有内容，说明有错
		if(errors.size()>0) {
			req.setAttribute("msg", errors.get("verifycode"));
			req.setAttribute("user", formUser);
			return "/jsps/user/login.jsp";
		}
		//开始判断
		if(null==user) {
			req.setAttribute("msg", "用户名或密码错误");
			req.setAttribute("user", formUser);
			return "/jsps/user/login.jsp";
		}else if(!user.isStatus()) {
			req.setAttribute("msg", "您还没有激活");
			req.setAttribute("user", formUser);
			return "/jsps/user/login.jsp";
		}
		//保存当前查询结果的user到session中
		req.getSession().setAttribute("sessionUser", user);
		//保存当前用户名称到cookie中，注意编码问题
		String loginname = user.getLoginname();
		loginname = URLEncoder.encode(loginname, "utf-8");
		Cookie cookie = new Cookie("loginname",loginname);
		//cookie设置年龄
		cookie.setMaxAge(60*60*24*2);//保存2天,单位是秒
		resp.addCookie(cookie);
		//重定向到主页
		return "/index.jsp";
	}
	/**
	 * 退出功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//将session销毁
		req.getSession().invalidate();
		//重定向
		return "/jsps/user/login.jsp";
	}
	/**
	 * 修改密码
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1、封装表单数据到user中
		 * 2、从session中获取uid
		 * 3、使用uid和表单中的oldPass和newPass来调用service方法
		 * >如果出现异常，保存异常信息到request中，转发到pwd.jsp
		 * 4、保存信息到request中
		 * 5、转发到pwd.jsp
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User) req.getSession().getAttribute("sessionUser");
		//如果没有登陆，返回登陆界面，显示错误信息
		if(null==user) {
			req.setAttribute("msg","您还没有登录！");
			return "/jsps/user/login.jsp";
		}
		try {
			userService.updatePassword(user.getUid(),formUser.getLoginpass(),formUser.getNewpass());
			req.setAttribute("msg", "修改密码成功");
			req.setAttribute("code","success");
			return "/jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());//保存异常信息到request中
			req.setAttribute("user", formUser);	//回显信息
			return "/jsps/user/pwd.jsp";
		}
	}
	/**
	 * 登陆校验方法，内容等自己完成
	 * @param formUser
	 * @param session
	 * @return
	 */
	private Map<String,String> validateLogin(User formUser,HttpSession session){
		Map<String,String> errors = new HashMap<String,String>();
		//校验验证码
		String verifyCode = formUser.getVerifyCode();
		String vCode = "";
		if(null!=session.getAttribute("vCode")) {
			vCode = (String) session.getAttribute("vCode");
		}else {
			errors.put("verifycode", "获取验证码图片内容失败！");
			return errors;
		}
		if(null==verifyCode||verifyCode.trim().isEmpty()) {
			errors.put("verifycode", "验证码不能为空");
		}else if(!vCode.equalsIgnoreCase(verifyCode)) {
			errors.put("verifycode", "验证码错误"+vCode);
		}
		return errors;
	}
	
}
