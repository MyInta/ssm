package cn.ssijri.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * dao中操作数据库，尽量不进行业务操作
 * @author 银涛
 *
 */
public class UserDao {
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//减工资操作
	public void lessenSalary() {
		String sql = "update t_salary set salary=salary+? where username=?";
		jdbcTemplate.update(sql,"-1000","小马");
		
	}
	//增加工资操作
	public void addSalary() {
		String sql = "update t_salary set salary=salary+? where username=?";
		jdbcTemplate.update(sql,"1000","小王");
	}
}
