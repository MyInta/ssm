package cn.ssijri.jdbc;

public class User {
	
	private String userName;
	private String passWord;
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Override
	public String toString() {
		return userName+"-->"+passWord;
	}
}
