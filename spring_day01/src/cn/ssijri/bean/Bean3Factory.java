package cn.ssijri.bean;

public class Bean3Factory {
	
	//测试下，构造器私有化对配置文件有无影响（经测试，无碍）
	private Bean3Factory(){
		
	}
	//实例工厂，非静态方法，返回Bean3对象。
	public Bean3 getBean3() {
		return new Bean3();
	}
}
