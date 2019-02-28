package cn.ssijri.property;

/**
 * set注入方式
 * @author 银涛
 *
 */
public class PropertyDemo2 {
	
	private String userName;
	
	//经测试，与传参名称无关，xml配置内name为需要注入的参数名称，即uerName
	public void setUserName(String user) {
		this.userName = user;
	}
	public void test1() {
		System.out.println("Demo2..."+userName);
	}
}
