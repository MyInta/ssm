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
	 * jdbc��������
	 */
//	@Test
//	public void testJdbc() {
//		
//		Connection conn = null;
//		PreparedStatement psmt = null;
//		ResultSet rs = null;		
//		try {
	//		//1���������ݿ�����
//			Class.forName("com.mysql.jdbc.Driver");
	//		//2���������ݿ�
//			conn = DriverManager.getConnection("jdbc:mysql:///test01", "root", "13201230");
//			//3��sql���
	//		String sql = "select * from t_user where username=?";
	//		//4��Ԥ����sql
//			psmt=conn.prepareStatement(sql);
	//		//5������sqlֵ
//			psmt.setString(1, "С��");
	//		//6��ִ��
//			rs = psmt.executeQuery();
//			//7�����������
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
		//�������ݿ���Ϣ
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql:///test01");
		dataSource.setUsername("root");
		dataSource.setPassword("13201230");
		
		//����JdbcTemplate������������Դ
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		//����jdbcTemplate������ķ���ʵ�ֲ���
		//����sql���
		
		/**
		 * ��ѯ����
		 */
//		String sql = "select count(*) from t_user";
//		int rows = jdbcTemplate.queryForObject(sql,Integer.class);
//		System.out.println(rows);
		
		/**
		 * ��ѯ�����
		 */
//		String sql = "select * from t_user where username=?";
//		User user = (User) jdbcTemplate.queryForObject(sql, new MyRowMapper(),"С��");
//		System.out.println(user);
		/**
		 * ��ѯ���list����
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
