package cn.ssijri.ioc;

/**
 * �������ô�����ʽ bean id class
 * �����Ǵ����չ��� Ĭ���пչ��죬�����Ƿ�˽���޹�
 * �Ҵ�������ʱ��Ϊ���Ǵ��ι��죬���ö���Ϊ��ֵ
 * ���ɾ���չ�������ֻ�д��ι���ʱ������ͨ�����൱���ÿչ�������������
 * ������ʧ�ܣ�
 * @author ����
 *
 */
public class User {
	
	private String name;
	//�����ļ������޲ι���ʱ����ù����Ƿ�˽���޹�
	private User() {
		
	}
	public User(String named) {
		this();
		this.name = named;
	}
	
	public void add() {
		System.out.println("add..."+this.name);
	}
	
	public static void main(String[] args) {
		User u = new User("nihao");
		u.add();
	}
}
