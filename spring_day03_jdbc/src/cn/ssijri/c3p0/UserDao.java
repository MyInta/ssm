package cn.ssijri.c3p0;

import org.springframework.jdbc.core.JdbcTemplate;


public class UserDao {
	//创建对象交给了spring属性注入
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void add() {
		System.out.println("add...");
		String sql = "insert into t_user(username,pwd) values(?,?)";
		jdbcTemplate.update(sql,"小蓝","1036");
	}
}
