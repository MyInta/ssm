package cn.ssijri.mybatis.mapper;


import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.ssijri.mybatis.po.Orders;
import cn.ssijri.mybatis.po.OrdersCustom;
import cn.ssijri.mybatis.po.User;

public class OrderCustomMapperTest {

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
	public void testFindOrdersUser() throws Exception {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrderCustomMapper orderCustomMapper = sqlSession.getMapper(OrderCustomMapper.class);
		
		List<OrdersCustom> list = orderCustomMapper.findOrdersUser();
		
		System.out.println(list);
	}
	@Test
	public void testFindOrdersUserResultMap() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrderCustomMapper orderCustomMapper = sqlSession.getMapper(OrderCustomMapper.class);
		
		List<Orders> list = orderCustomMapper.findOrdersUserResultMap();
		
		System.out.println(list);
	}
	@Test
	public void testFindOrdersAndOrderDetailResultMap() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrderCustomMapper orderCustomMapper = sqlSession.getMapper(OrderCustomMapper.class);
		
		List<Orders> list = orderCustomMapper.findOrdersAndOrderDetailResultMap();
		
		System.out.println(list);
	}
	@Test
	public void testFindOrdersUserLazyLoading() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrderCustomMapper orderCustomMapper = sqlSession.getMapper(OrderCustomMapper.class);
		
		List<Orders> list = orderCustomMapper.findOrdersUserLazyLoading();
		
		for(Orders order:list) {
			User user = order.getUser();
			System.out.println(user);
		}
		
	}

}