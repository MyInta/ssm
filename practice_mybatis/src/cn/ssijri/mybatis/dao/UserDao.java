package cn.ssijri.mybatis.dao;

import cn.ssijri.mybatis.po.User;

/**
 * dao�ӿ� �û�����
 * @author ����
 *
 */
public interface UserDao {

	
	//����ID��ѯ�û���Ϣ
	public User findUserById(int id)throws Exception;

	//����û���Ϣ
	public void insertUser(User user)throws Exception;
	
	//ɾ���û���Ϣ
	public void deleteUser(int id)throws Exception;
}
