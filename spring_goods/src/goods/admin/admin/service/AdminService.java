package goods.admin.admin.service;

import java.sql.SQLException;

import goods.admin.admin.dao.AdminDao;
import goods.admin.admin.domain.Admin;

public class AdminService {

	private AdminDao adminDao = new AdminDao();
	
	/**
	 * ��¼����
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin) {
		try {
			return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
		throw new RuntimeException(e);
		}
	}
}
