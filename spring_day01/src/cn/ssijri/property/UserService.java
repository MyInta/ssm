package cn.ssijri.property;

/**
 * 测试对象注入，将对象作为一个属性，set属性内容实现
 * @author 银涛
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
