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
    // 此方法是在执行testFindUserById之前执行
    @Before
    public void setUp() throws Exception {
        // 创建sqlSessionFactory

        // mybatis配置文件
        String resource = "SqlMapConfig.xml";
        // 得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        // 创建会话工厂，传入mybatis的配置文件信息
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
