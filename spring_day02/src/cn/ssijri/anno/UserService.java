package cn.ssijri.anno;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * ע�ⷽʽ�Ķ���ע�룬��Ҫע�⣺��ע�����Դ������Ҫ��ע��
 * @author ����
 *
 */
//@Component(value="userService")
@Service(value="userService")
public class UserService {
	
	/**
	 * ʹ��Autowired�󣬲���Ҫset��������ʵ���½����Զ���
	 * �൱�ڰ�refĿ�����
	 */
//	@Autowired
	@Resource(name="userDao")
	private UserDao userDao;
	
	public void add() {
		System.out.println("Service add������");
		userDao.add();
	}
}
