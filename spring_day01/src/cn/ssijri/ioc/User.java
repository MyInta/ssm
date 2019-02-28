package cn.ssijri.ioc;

/**
 * 基本配置创建方式 bean id class
 * 上面是创建空构造 默认有空构造，并且是否私有无关
 * 且创建对象时因为不是带参构造，调用对象为空值
 * 如果删掉空构造器，只有带参构造时候用普通创建相当于用空构造器创建对象
 * 创建会失败！
 * @author 银涛
 *
 */
public class User {
	
	private String name;
	//配置文件调用无参构造时，与该构造是否私有无关
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
