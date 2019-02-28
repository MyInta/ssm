package cn.itcast.goods.user.domain;

/**
 * 用户模块实体类
 * 
 * 属性由来 1、t_user表，表数据封装到此pojo中
 * 			2、所有该模块表单
 * @author 银涛
 *
 */
public class User {

	//主键
	private String uid;
	//登录名
	private String loginname;
	//登陆密码
	private String loginpass;
	//邮箱
	private String email;
	//状态 true 激活 false未激活
	private boolean status;
	//激活码,唯一值，每个用户只有一个。
	private String activationCode;
	
	//确认密码
	private String reloginpass;
	//验证码
	private String verifyCode;
	//修改密码表单 新密码
	private String newpass;
	
	//空构造器
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
