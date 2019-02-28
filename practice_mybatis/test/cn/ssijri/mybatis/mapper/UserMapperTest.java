package cn.ssijri.mybatis.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.ssijri.mybatis.po.User;
import cn.ssijri.mybatis.po.UserCustom;
import cn.ssijri.mybatis.po.UserQueryVo;

public class UserMapperTest {
	 private SqlSessionFactory sqlSessionFactory;

	    // �˷�������ִ��testFindUserById֮ǰִ��
	    @Before
	    public void setUp() throws Exception {
	        // ����sqlSessionFactory

	        // mybatis�����ļ�
	        String resource = "SqlMapConfig.xml";
	        // �õ������ļ���
	        InputStream inputStream = Resources.getResourceAsStream(resource);

	        // �����Ự����������mybatis�������ļ���Ϣ
	        sqlSessionFactory = new SqlSessionFactoryBuilder()
	                .build(inputStream);
	    }

	    @Test
	    public void testFindUserById() throws Exception {

	        SqlSession sqlSession = sqlSessionFactory.openSession();

	        //����UserMapper����mybatis�Զ�����mapper�������
	        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

	        //����userMapper�ķ���

	        User user = userMapper.findUserById(1);

	        System.out.println(user);

	    }
	    
	    //�����û����Ʋ�ѯ
	    @Test
	    public void testFindUserByName() throws Exception {
	    	
	    	SqlSession sqlSession = sqlSessionFactory.openSession();
	    	
	    	//����UserMapper����mybatis�Զ�����mapper�������
	    	UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
	    	
	    	//����userMapper�ķ���
	    	
	    	List<User> userList = userMapper.findUserByName("��");
	    	
	    	System.out.println(userList);
	    	
	    }
	    //�û���Ϣ���ۺϲ�ѯ
	    @Test
	    public void testFindUserList() throws Exception {

	        SqlSession sqlSession = sqlSessionFactory.openSession();

	        //����UserMapper����mybatis�Զ�����mapper�������
	        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

	        //������װ�������ò�ѯ����
	        UserQueryVo userQueryVo = new UserQueryVo();
	        UserCustom userCustom = new UserCustom();
	        //��������ʹ�ö�̬sql�����������ĳ��ֵ����������ƴ����sql��
	        userCustom.setSex("1");
	        //userCustom.setUsername("����");


	        userQueryVo.setUserCustom(userCustom);
	        //����userMapper�ķ���

	        List<UserCustom> list = userMapper.findUserList(userQueryVo);
	        //List<UserCustom> list = userMapper.findUserList(null);

	        System.out.println(list);

	    }

	    //�û���Ϣ�ۺϲ�ѯ����
	    @Test
	    public void testFindUserCount() throws Exception {

	        SqlSession sqlSession = sqlSessionFactory.openSession();

	        //����UserMapper����mybatis�Զ�����mapper�������
	        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

	        //������װ�������ò�ѯ����
	        UserQueryVo userQueryVo = new UserQueryVo();
	        UserCustom userCustom = new UserCustom();
	        //��������ʹ�ö�̬sql�����������ĳ��ֵ����������ƴ����sql��
	        userCustom.setSex("1");
	        userCustom.setUsername("С");
	        List<Integer> ids = new ArrayList<>();
	        ids.add(1);
	        ids.add(5);
	        ids.add(16);
	        ids.add(25);
	        userQueryVo.setIds(ids);
	        userQueryVo.setUserCustom(userCustom);
	        //����userMapper�ķ���

	        int count = userMapper.findUserCount(userQueryVo);

	        System.out.println(count);

	    }
	    //����id��ѯ�û���Ϣ��ʹ��resultMap���
	    @Test
	    public void findUserByIdResultMap() throws Exception {
	    	
	    	SqlSession sqlSession = sqlSessionFactory.openSession();
	    	
	    	//����UserMapper����mybatis�Զ�����mapper�������
	    	UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
	    	
	    	User user = userMapper.findUserByIdResultMap(24);
	    	System.out.println(user);
	    	
	    }


	  
}
