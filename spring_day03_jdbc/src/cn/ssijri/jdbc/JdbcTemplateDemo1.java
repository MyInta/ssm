package cn.ssijri.jdbc;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcTemplateDemo1 {
	
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
		 * 插入操作
		 */
//		String sql = "insert into t_user(username,pwd) values(?,?)";
//		int rows = jdbcTemplate.update(sql,"jdbcTemplate","666");
//		System.out.println(rows);
		/**
		 * 修改操作
		 */
//		String sql = "update t_user set pwd=? where username=?";
//		int rows = jdbcTemplate.update(sql,"999","jdbcTemplate");
//		System.out.println(rows);
		/**
		 * 删除操作
		 */
//		String sql = "delete from t_user where username=?";
//		int rows = jdbcTemplate.update(sql,"jdbcTemplate");
//		System.out.println(rows);
		/**
		 * 查询操作
		 */
		String sql = "select count(*) from t_user";
		int rows = jdbcTemplate.queryForObject(sql,Integer.class);
		System.out.println(rows);
	}
}
