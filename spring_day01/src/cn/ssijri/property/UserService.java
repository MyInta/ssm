package cn.ssijri.property;

/**
 * ���Զ���ע�룬��������Ϊһ�����ԣ�set��������ʵ��
 * @author ����
 *
 */
public class UserService {
	
	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void add() {
		System.out.println("service....");
		userDao.add();
	}
}
