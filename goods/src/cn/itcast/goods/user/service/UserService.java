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
 * �û�ģ��ҵ���
 * @author ����
 *
 */
public class UserService {

	//service������dao
	private UserDao userDao = new UserDao();
	/**
	 * �޸�����
	 * @param uid
	 * @param oldpass ������
	 * @param newpass	������
	 * @throws UserException
	 */
	public void updatePassword(String uid,String oldpass,String newpass) throws UserException {
		try {
			/**
			 *1��У�������� 
			 */
			boolean bool = userDao.findByUidAndPass(uid, oldpass);
			if(!bool) {
				throw new UserException("У�����������");
			}
			/**
			 * 2����������
			 */
			userDao.updatePassword(uid,newpass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ��¼����
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
	 * �����
	 * @param code
	 * @throws UserException 
	 * @throws SQLException 
	 */
	public void activation(String code) throws UserException{
		/**
		 * 1��ͨ���������ѯ�û�
		 * 2�����userΪnull,�׳��쳣��Ϣ�����������
		 * 3���鿴�û�״̬status���Ϊtrue���׳��쳣���벻Ҫ���μ��
		 * 4���޸��û�״̬Ϊtrue
		 */
		User user;
		try {
			user = userDao.findByCode(code);
			if(null==user)throw new UserException("��Ч�ļ����룡");
			if(user.isStatus())throw new UserException("�벻Ҫ���μ���~");
			userDao.updateStatus(user.getUid(), true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * �û���ע��У��
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
	 * EmailУ��
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
	 * ע���û�
	 * @param user
	 */
	public void regist(User user) {
		/**
		 * 1�����ݲ�ȫ
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
		/**
		 * 2�������ݿ����
		 */
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
		/**
		 * 3�����ʼ�
		 */
		//�������ļ����ݼ��ص�prop��
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1); 
		}
		
		String host=prop.getProperty("host");//������������
		String name=prop.getProperty("username");//��¼��
		String pass =prop.getProperty("password");//��½����
		//��½�ʼ������������session
		Session session = MailUtils.createSession(host, name, pass);
		//����Mail����
		String from=prop.getProperty("from");
		String to=user.getEmail();
		String subject=prop.getProperty("subject");
		//MessageFormat.format(arg1,arg2); arg1Ϊģ�壬arg2�滻ģ���еĴ�����0��1... ...���滻{0}
		String content=MessageFormat.format(prop.getProperty("content"),user.getActivationCode());
		Mail mail = new Mail(from,to,subject,content);
		//�����ʼ�
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e); 
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
		
		
	}
}
