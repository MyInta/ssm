package cn.ssijri.jdbc;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcTemplateDemo1 {
	
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
		 * �������
		 */
//		String sql = "insert into t_user(username,pwd) values(?,?)";
//		int rows = jdbcTemplate.update(sql,"jdbcTemplate","666");
//		System.out.println(rows);
		/**
		 * �޸Ĳ���
		 */
//		String sql = "update t_user set pwd=? where username=?";
//		int rows = jdbcTemplate.update(sql,"999","jdbcTemplate");
//		System.out.println(rows);
		/**
		 * ɾ������
		 */
//		String sql = "delete from t_user where username=?";
//		int rows = jdbcTemplate.update(sql,"jdbcTemplate");
//		System.out.println(rows);
		/**
		 * ��ѯ����
		 */
		String sql = "select count(*) from t_user";
		int rows = jdbcTemplate.queryForObject(sql,Integer.class);
		System.out.println(rows);
	}
}
