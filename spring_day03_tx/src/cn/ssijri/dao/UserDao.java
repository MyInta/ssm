package cn.ssijri.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * dao�в������ݿ⣬����������ҵ�����
 * @author ����
 *
 */
public class UserDao {
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//�����ʲ���
	public void lessenSalary() {
		String sql = "update t_salary set salary=salary+? where username=?";
		jdbcTemplate.update(sql,"-1000","С��");
		
	}
	//���ӹ��ʲ���
	public void addSalary() {
		String sql = "update t_salary set salary=salary+? where username=?";
		jdbcTemplate.update(sql,"1000","С��");
	}
}
