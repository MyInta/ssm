package goods.admin.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import goods.admin.admin.domain.Admin;
import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {

	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 通过管理员用户名和密码查询
	 * @param admin
	 * @param adminpwd
	 * @return
	 * @throws SQLException
	 */
	public Admin find(String admin,String adminpwd) throws SQLException {
		String sql ="select * from t_admin where adminname= ? and adminpwd =?";
		return qr.query(sql, new BeanHandler<Admin>(Admin.class),admin,adminpwd);
	}
	
}
