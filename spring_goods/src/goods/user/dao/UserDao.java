package goods.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import goods.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * �û�ģ��־ò�
 * @author ����
 *
 */
public class UserDao {

	//ʹ���Զ���ķ�װ���ݿ�������� QueryRunner->commons.dbutils��TxQueryRunner->itcast.jdbc 
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * ��uid��password��ѯ
	 * @param uid
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean findByUidAndPass(String uid,String password) throws SQLException {
		String sql = "select count(*) from t_user where uid=? and loginpass=?";
		Number number =  (Number) qr.query(sql,new ScalarHandler() ,uid,password);
		return number.intValue()>0;
	}
	/**
	 * ��uid��password����������
	 * @param uid
	 * @param password
	 * @throws SQLException 
	 */
	public void updatePassword(String uid,String password) throws SQLException {
		String sql = "update t_user set loginpass=? where uid=?";
		qr.update(sql,password,uid);
	}
	
	/**
	 * ͨ�����������û���Ϣ
	 * @return
	 * @throws SQLException 
	 */
	public User findByCode(String code) throws SQLException {
		String sql = "select * from t_user where activationCode=?";
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}
	/**
	 * ���û����������ѯ
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws SQLException 
	 */
	public User findByLoginnameAndLoginpass(String loginname,String loginpass) throws SQLException {
		String sql = "select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql, new BeanHandler<User>(User.class),loginname,loginpass);
	}
	/**
	 * �����û�״̬
	 * @param uid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String uid,boolean status) throws SQLException {
		String sql = "update t_user set status=? where uid=?";
		qr.update(sql,status,uid);
	}
	
	/**
	 * У���û����Ƿ�ע��
	 * @param loginName
	 * @return
	 * @throws SQLException 
	 */
	public boolean ajaxValidateLoginName(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(),loginname);
		return number.intValue()==0;
	}
	/**
	 * У��email�Ƿ�ע��
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateEmail(String email) throws SQLException {
		String sql = "select count(1) from t_user where email=?";
		Number number = (Number) qr.query(sql, new ScalarHandler(),email);
		return number.intValue()==0;
	}
	/**
	 * �����û�
	 * @param user
	 * @throws SQLException 
	 */
	public void add(User user) throws SQLException {
		String sql ="insert into t_user value(?,?,?,?,?,?)";
		Object[] params = {user.getUid(),user.getLoginname(),user.getLoginpass()
				,user.getEmail(),user.isStatus(),user.getActivationCode()};
		qr.update(sql, params);
	}
}