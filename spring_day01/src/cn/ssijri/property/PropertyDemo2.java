package cn.ssijri.property;

/**
 * setע�뷽ʽ
 * @author ����
 *
 */
public class PropertyDemo2 {
	
	private String userName;
	
	//�����ԣ��봫�������޹أ�xml������nameΪ��Ҫע��Ĳ������ƣ���uerName
	public void setUserName(String user) {
		this.userName = user;
	}
	public void test1() {
		System.out.println("Demo2..."+userName);
	}
}
