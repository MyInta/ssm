package cn.ssijri.bean;

public class Bean3Factory {
	
	//�����£�������˽�л��������ļ�����Ӱ�죨�����ԣ��ް���
	private Bean3Factory(){
		
	}
	//ʵ���������Ǿ�̬����������Bean3����
	public Bean3 getBean3() {
		return new Bean3();
	}
}
