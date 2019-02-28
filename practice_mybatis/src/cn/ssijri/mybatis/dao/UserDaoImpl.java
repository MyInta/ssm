package cn.ssijri.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.ssijri.mybatis.po.User;

/**
 * UserDaoʵ����
 * @author ����
 *
 */
public class UserDaoImpl implements UserDao{
	
	//��Ҫ��Dao��ע��SqlSessionFactory
	//����ͨ��������ע��

	private SqlSessionFactory sqlSessionFactory;
	
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserById(int id) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = sqlSession.selectOne("test.findUserById", id);
		//�ͷ���Դ
		sqlSession.close();
		return user;
	}

	@Override
	public void insertUser(User user) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//�������
	    sqlSession.insert("test.insertUser", user);
        // �ύ����
        sqlSession.commit();
		//�ͷ���Դ
		sqlSession.close();
	}

	@Override
	public void deleteUser(int id) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//ɾ������
		sqlSession.delete("test.deleteUser", id);
	    // �ύ����
        sqlSession.commit();
		//�ͷ���Դ
		sqlSession.close();
		
	}

}
