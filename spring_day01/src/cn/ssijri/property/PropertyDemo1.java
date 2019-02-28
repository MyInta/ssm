package cn.ssijri.property;

/**
 * 经测试，有参构造存在而无参构造不在的时候，xml使用有参构造注入可以创建对象
 * 											 但是如果使用无参构造则不能创建
 * xml配置中使用得name传入参数名时，与javaBean内的有参构造器的参数名一致
 * 另外调用有参构造创建对象时候，使用的属性用constructor-arg注入
 * 调用方法时若用到该属性，则可以成功取出属性内容
 * 光无参构造，调用属性内容时传值失败。
 * @author 银涛
 *
 */
public class PropertyDemo1 {
	
	private String usersName;//故意加个s，发现xml参数名与其无关
	
	//注意因为空构造一旦建成就会显示syso，所以在bean1.xml配置文件运行时会显示其内容，
	//不管是不是在测试这个类对象
	private PropertyDemo1() {
		System.out.println("hello~先加载无参构造测试");
	}
	
	private PropertyDemo1(String userName) {//但是如果这里参数改变，则影响xml配置
		this();
		this.usersName = userName;
	}
	
	public void test1() {
		System.out.println("Demo1..."+usersName);
	}
}
