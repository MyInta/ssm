package cn.ssijri.service;

import org.springframework.transaction.annotation.Transactional;

import cn.ssijri.dao.UserDao;

/**
 * service内尽量写业务逻辑而不写数据库操作
 * @author 银涛
 *
 */
//@Transactional	//bean3anno.xml(注解形式的)
public class UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//业务逻辑
	public void account() {
		//减少工资
		userDao.lessenSalary();
		
		//如果异常，测试有无回滚
		int i = 10/0;
		
		//增加工资
		userDao.addSalary();
	}
}
