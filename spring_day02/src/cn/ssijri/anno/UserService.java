package cn.ssijri.anno;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 注解方式的对象注入，需要注意：所注入对象源类上需要有注解
 * @author 银涛
 *
 */
//@Component(value="userService")
@Service(value="userService")
public class UserService {
	
	/**
	 * 使用Autowired后，不需要set方法就能实现新建属性对象
	 * 相当于绑定ref目标对象
	 */
//	@Autowired
	@Resource(name="userDao")
	private UserDao userDao;
	
	public void add() {
		System.out.println("Service add。。。");
		userDao.add();
	}
}
