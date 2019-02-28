package cn.ssijri.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.ssijri.mybatis.po.User;

/**
 * UserDao实现类
 * @author 银涛
 *
 */
public class UserDaoImpl implements UserDao{
	
	//需要向Dao中注入SqlSessionFactory
	//这里通过构造器注入

	private SqlSessionFactory sqlSessionFactory;
	
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserById(int id) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = sqlSession.selectOne("test.findUserById", id);
		//释放资源
		sqlSession.close();
		return user;
	}

	@Override
	public void insertUser(User user) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//插入操作
	    sqlSession.insert("test.insertUser", user);
        // 提交事务
        sqlSession.commit();
		//释放资源
		sqlSession.close();
	}

	@Override
	public void deleteUser(int id) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//删除操作
		sqlSession.delete("test.deleteUser", id);
	    // 提交事务
        sqlSession.commit();
		//释放资源
		sqlSession.close();
		
	}

}
