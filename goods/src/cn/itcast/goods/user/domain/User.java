package cn.itcast.goods.user.domain;

/**
 * �û�ģ��ʵ����
 * 
 * �������� 1��t_user�������ݷ�װ����pojo��
 * 			2�����и�ģ���
 * @author ����
 *
 */
public class User {

	//����
	private String uid;
	//��¼��
	private String loginname;
	//��½����
	private String loginpass;
	//����
	private String email;
	//״̬ true ���� falseδ����
	private boolean status;
	//������,Ψһֵ��ÿ���û�ֻ��һ����
	private String activationCode;
	
	//ȷ������
	private String reloginpass;
	//��֤��
	private String verifyCode;
	//�޸������ ������
	private String newpass;
	
	//�չ�����
	public User() {
	}
		
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getReloginpass() {
		return reloginpass;
	}

	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

}
