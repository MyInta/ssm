package cn.ssijri.service;

import org.springframework.transaction.annotation.Transactional;

import cn.ssijri.dao.UserDao;

/**
 * service�ھ���дҵ���߼�����д���ݿ����
 * @author ����
 *
 */
//@Transactional	//bean3anno.xml(ע����ʽ��)
public class UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//ҵ���߼�
	public void account() {
		//���ٹ���
		userDao.lessenSalary();
		
		//����쳣���������޻ع�
		int i = 10/0;
		
		//���ӹ���
		userDao.addSalary();
	}
}
