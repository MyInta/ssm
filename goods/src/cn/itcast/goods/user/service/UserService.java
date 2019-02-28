package cn.itcast.goods.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.management.RuntimeErrorException;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.user.dao.UserDao;
import cn.itcast.goods.user.domain.User;
import cn.itcast.goods.user.service.exception.UserException;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

/**
 * 用户模块业务层
 * @author 银涛
 *
 */
public class UserService {

	//service依赖于dao
	private UserDao userDao = new UserDao();
	/**
	 * 修改密码
	 * @param uid
	 * @param oldpass 旧密码
	 * @param newpass	新密码
	 * @throws UserException
	 */
	public void updatePassword(String uid,String oldpass,String newpass) throws UserException {
		try {
			/**
			 *1、校验老密码 
			 */
			boolean bool = userDao.findByUidAndPass(uid, oldpass);
			if(!bool) {
				throw new UserException("校验老密码错误！");
			}
			/**
			 * 2、更新密码
			 */
			userDao.updatePassword(uid,newpass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 登录功能
	 * @param user
	 * @return
	 */
	public User login(User user) {
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 激活功能
	 * @param code
	 * @throws UserException 
	 * @throws SQLException 
	 */
	public void activation(String code) throws UserException{
		/**
		 * 1、通过激活码查询用户
		 * 2、如果user为null,抛出异常信息（激活码错误）
		 * 3、查看用户状态status如果为true则抛出异常（请不要二次激活）
		 * 4、修改用户状态为true
		 */
		User user;
		try {
			user = userDao.findByCode(code);
			if(null==user)throw new UserException("无效的激活码！");
			if(user.isStatus())throw new UserException("请不要二次激活~");
			userDao.updateStatus(user.getUid(), true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 用户名注册校验
	 * @param loginName
	 * @return
	 */
	public boolean ajaxValidateLoginName(String loginname){
		try {
			return userDao.ajaxValidateLoginName(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Email校验
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email){
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 注册用户
	 * @param user
	 */
	public void regist(User user) {
		/**
		 * 1、数据补全
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
		/**
		 * 2、向数据库插入
		 */
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
		/**
		 * 3、发邮件
		 */
		//把配置文件内容加载到prop中
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1); 
		}
		
		String host=prop.getProperty("host");//服务器主机名
		String name=prop.getProperty("username");//登录名
		String pass =prop.getProperty("password");//登陆密码
		//登陆邮件服务器。获得session
		Session session = MailUtils.createSession(host, name, pass);
		//创建Mail对象
		String from=prop.getProperty("from");
		String to=user.getEmail();
		String subject=prop.getProperty("subject");
		//MessageFormat.format(arg1,arg2); arg1为模板，arg2替换模板中的大括号0、1... ...即替换{0}
		String content=MessageFormat.format(prop.getProperty("content"),user.getActivationCode());
		Mail mail = new Mail(from,to,subject,content);
		//发送邮件
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e); 
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
		
		
	}
}
