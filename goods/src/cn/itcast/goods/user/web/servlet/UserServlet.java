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
 * �û�ģ����Ʋ�
 * @author ����
 *
 */
public class UserServlet extends BaseServlet {
	
	//��Servlet��ע��service����
	private UserService userService = new UserService();
	/**
	 * �û����Ƿ�ע��У��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateLoginName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1������û���
		 */
		String loginname = req.getParameter("loginname");
		/**
		 * 2��ͨ��service��ȡУ��
		 */
		boolean b = userService.ajaxValidateLoginName(loginname);
		/**
		 * 3�������˷���
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * Emial�Ƿ�ע��У��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1����������ַ
		 */
		String email = req.getParameter("email");
		/**
		 * 2��ͨ��service��ȡУ��
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/**
		 * 3�������˷���
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * ��֤���Ƿ���ȷУ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1����ȡ�������У����
		 */
		String verifyCode = req.getParameter("verifyCode");
		/**
		 * 2����ȡͼƬ����ʵ����֤��
		 */
		String vCode = (String) req.getSession().getAttribute("vCode");
		/**
		 * 3�����к��Դ�Сд�Ƚϣ��õ����
		 */
		boolean b = verifyCode.equalsIgnoreCase(vCode);
		/**
		 * 4�����͸��ͻ���
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * ע�Ṧ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1����װ������
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/**
		 * 2��У��֮,����session������Ϊ�˻����֤��ͼƬ��ʵֵ
		 */
		Map<String,String> errors = validateRegist(formUser,req.getSession());
		//���errors map�������ݣ�˵���д�
		if (/* errors!=null&& */errors.size()>0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "/jsps/user/regist.jsp";
		}
		/**
		 * 3��ʹ��service���ҵ��
		 */
		userService.regist(formUser);
		/**
		 * 4������ɹ���Ϣ��ת��msg.jspҳ����
		 */
		req.setAttribute("code","success");
		req.setAttribute("msg", "ע��ɹ�����������������");
		return "/jsps/msg.jsp";
	
	}
	/**
	 * ע��У�飬�Ա��ֶ����У��,��ǰ�ֶ���Ϊkey��������ϢΪvalue
	 * @param formUser
	 * @param session
	 * @return
	 */
	private Map<String,String> validateRegist(User formUser,HttpSession session){
		Map<String,String> errors = new HashMap<String,String>();
		/**
		 * 1��У���¼��
		 */
		String loginname = formUser.getLoginname();
		if(null==loginname||loginname.trim().isEmpty()) {
			errors.put("loginname", "�û�������Ϊ��");
		}else if(loginname.length()<3||loginname.length()>12){
			errors.put("loginname", "�û�������Ҫ��3-12֮��");
		}else if(!userService.ajaxValidateLoginName(loginname)) {
			errors.put("loginname", "�û�����ע��");
		}
		/**
		 * 2��У���½����
		 */
		String loginpass = formUser.getLoginpass();
		if(null==loginpass||loginpass.trim().isEmpty()) {
			errors.put("loginpass", "���벻��Ϊ��");
		}else if(loginpass.length()<6||loginpass.length()>12){
			errors.put("loginpass", "���볤��Ҫ��3-12֮��");
		}
		/**
		 * 3��ȷ������У��
		 */
		String reloginpass = formUser.getReloginpass();
		if(null==reloginpass||reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "ȷ�����벻��Ϊ��");
		}else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "�������벻һ��");
		}
		/**
		 * 4��ȷ������
		 */
		String email = formUser.getEmail();
		if(null==email||email.trim().isEmpty()) {
			errors.put("email", "���䲻��Ϊ��");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "�����ʽ����");
		}else if(!userService.ajaxValidateEmail(email)) {
			errors.put("email", "������ע��");
		}
		/**
		 * 5��ȷ��У����
		 */
		String verifyCode = formUser.getVerifyCode();
		String vCode = "";
		if(null!=session.getAttribute("vCode")) {
			vCode = (String) session.getAttribute("vCode");
		}else {
			errors.put("verifycode", "��ȡ��֤��ͼƬ����ʧ�ܣ�");
			return errors;
		}
		if(null==verifyCode||verifyCode.trim().isEmpty()) {
			errors.put("verifycode", "��֤�벻��Ϊ��");
		}else if(!vCode.equalsIgnoreCase(verifyCode)) {
			errors.put("verifycode", "��֤�����");
		}
		return errors;
	}
	
	/**
	 * �����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 1����ȡ������
		 * 2���ü��������service������ɼ���
		 * 3��������쳣�����浽request�У�ת����msg.jsp
		 * 4������ɹ���Ϣ��request�У�ת����msg.jsp��ʾ
		 */
		String code = req.getParameter("activationCode");
		try {
			userService.activation(code);
			req.setAttribute("code", "success");//����msg.jsp��code�ж���ʾ��
			req.setAttribute("msg", "����ɹ���~");
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");//����msg.jsp��code�ж���ʾ��
		}
		return "/jsps/msg.jsp";
	}
	/**
	 * �û���½����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1����װ�����ݵ�User
		 * 2��У�������
		 * 3��ʹ��service��ѯUser
		 * 4�����User���������������
		 * 	���������Ϣ���û������������
		 * 	�����û���Ϣ��Ϊ�˻���
		 * 	ת����login.jsp
		 * 5���������user�鿴״̬status
		 * 	���Ϊfalse�򱣴������Ϣ����δע��
		 * 	�����û���Ϣ��Ϊ�˻���
		 * 	ת����login.jsp
		 * 6����½�ɹ�
		 * 	���浱ǰ��ѯ�����user��session��
		 * 	���浱ǰ�û����Ƶ�cookie�У�ע���������
		 */
		//��װ����
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		//����userService����������ݿ��ڲ�ѯ�����user
		User user = userService.login(formUser);
		//У��(validateLoginУ�鷽��������֤����һ����)
		Map<String,String> errors = validateLogin(formUser,req.getSession());
		//���errors map�������ݣ�˵���д�
		if(errors.size()>0) {
			req.setAttribute("msg", errors.get("verifycode"));
			req.setAttribute("user", formUser);
			return "/jsps/user/login.jsp";
		}
		//��ʼ�ж�
		if(null==user) {
			req.setAttribute("msg", "�û������������");
			req.setAttribute("user", formUser);
			return "/jsps/user/login.jsp";
		}else if(!user.isStatus()) {
			req.setAttribute("msg", "����û�м���");
			req.setAttribute("user", formUser);
			return "/jsps/user/login.jsp";
		}
		//���浱ǰ��ѯ�����user��session��
		req.getSession().setAttribute("sessionUser", user);
		//���浱ǰ�û����Ƶ�cookie�У�ע���������
		String loginname = user.getLoginname();
		loginname = URLEncoder.encode(loginname, "utf-8");
		Cookie cookie = new Cookie("loginname",loginname);
		//cookie��������
		cookie.setMaxAge(60*60*24*2);//����2��,��λ����
		resp.addCookie(cookie);
		//�ض�����ҳ
		return "/index.jsp";
	}
	/**
	 * �˳�����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//��session����
		req.getSession().invalidate();
		//�ض���
		return "/jsps/user/login.jsp";
	}
	/**
	 * �޸�����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * 1����װ�����ݵ�user��
		 * 2����session�л�ȡuid
		 * 3��ʹ��uid�ͱ��е�oldPass��newPass������service����
		 * >��������쳣�������쳣��Ϣ��request�У�ת����pwd.jsp
		 * 4��������Ϣ��request��
		 * 5��ת����pwd.jsp
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User) req.getSession().getAttribute("sessionUser");
		//���û�е�½�����ص�½���棬��ʾ������Ϣ
		if(null==user) {
			req.setAttribute("msg","����û�е�¼��");
			return "/jsps/user/login.jsp";
		}
		try {
			userService.updatePassword(user.getUid(),formUser.getLoginpass(),formUser.getNewpass());
			req.setAttribute("msg", "�޸�����ɹ�");
			req.setAttribute("code","success");
			return "/jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());//�����쳣��Ϣ��request��
			req.setAttribute("user", formUser);	//������Ϣ
			return "/jsps/user/pwd.jsp";
		}
	}
	/**
	 * ��½У�鷽�������ݵ��Լ����
	 * @param formUser
	 * @param session
	 * @return
	 */
	private Map<String,String> validateLogin(User formUser,HttpSession session){
		Map<String,String> errors = new HashMap<String,String>();
		//У����֤��
		String verifyCode = formUser.getVerifyCode();
		String vCode = "";
		if(null!=session.getAttribute("vCode")) {
			vCode = (String) session.getAttribute("vCode");
		}else {
			errors.put("verifycode", "��ȡ��֤��ͼƬ����ʧ�ܣ�");
			return errors;
		}
		if(null==verifyCode||verifyCode.trim().isEmpty()) {
			errors.put("verifycode", "��֤�벻��Ϊ��");
		}else if(!vCode.equalsIgnoreCase(verifyCode)) {
			errors.put("verifycode", "��֤�����"+vCode);
		}
		return errors;
	}
	
}
