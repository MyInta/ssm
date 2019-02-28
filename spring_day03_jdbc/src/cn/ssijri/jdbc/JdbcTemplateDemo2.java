package cn.ssijri.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcTemplateDemo2 {
	
	/**
	 * jdbc基本操作
	 */
//	@Test
//	public void testJdbc() {
//		
//		Connection conn = null;
//		PreparedStatement psmt = null;
//		ResultSet rs = null;		
//		try {
	//		//1、加载数据库驱动
//			Class.forName("com.mysql.jdbc.Driver");
	//		//2、连接数据库
//			conn = DriverManager.getConnection("jdbc:mysql:///test01", "root", "13201230");
//			//3、sql语句
	//		String sql = "select * from t_user where username=?";
	//		//4、预编译sql
//			psmt=conn.prepareStatement(sql);
	//		//5、设置sql值
//			psmt.setString(1, "小明");
	//		//6、执行
//			rs = psmt.executeQuery();
//			//7、遍历结果集
//			while(rs.next()) {
//				String userName = rs.getString("username");
//				String passWord = rs.getString("pwd");
//				User user = new User();
//				user.setUserName(userName);
//				user.setPassWord(passWord);
//				System.out.println(user);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				rs.close();
//				psmt.close();
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//		}
//	}
	
	@Test
	public void add() {
		//设置数据库信息
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql:///test01");
		dataSource.setUsername("root");
		dataSource.setPassword("13201230");
		
		//创建JdbcTemplate对象，设置数据源
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		//调用jdbcTemplate对象里的方法实现操作
		//创建sql语句
		
		/**
		 * 查询操作
		 */
//		String sql = "select count(*) from t_user";
//		int rows = jdbcTemplate.queryForObject(sql,Integer.class);
//		System.out.println(rows);
		
		/**
		 * 查询结果集
		 */
//		String sql = "select * from t_user where username=?";
//		User user = (User) jdbcTemplate.queryForObject(sql, new MyRowMapper(),"小明");
//		System.out.println(user);
		/**
		 * 查询结果list集合
		 */
		String sql = "select * from t_user";
		List<User> list = jdbcTemplate.query(sql, new MyRowMapper());
		System.out.println(list);
	}
}

class MyRowMapper implements RowMapper{

	@Override
	public User mapRow(ResultSet rs, int num) throws SQLException {
		String userName = rs.getString("username");
		String passWord = rs.getString("pwd");
		User user = new User();
		user.setUserName(userName);
		user.setPassWord(passWord);
		return user;
	}
	
}
