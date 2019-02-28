package cn.ssijri.entity;

public class User {

	private String username;
	private String password;
	private String address;
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "username:"+username+" password:"+password+" address:"+address;
	}
}
