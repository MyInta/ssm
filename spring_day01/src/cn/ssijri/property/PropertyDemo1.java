package cn.ssijri.property;

/**
 * �����ԣ��вι�����ڶ��޲ι��첻�ڵ�ʱ��xmlʹ���вι���ע����Դ�������
 * 											 �������ʹ���޲ι������ܴ���
 * xml������ʹ�õ�name���������ʱ����javaBean�ڵ��вι������Ĳ�����һ��
 * ��������вι��촴������ʱ��ʹ�õ�������constructor-argע��
 * ���÷���ʱ���õ������ԣ�����Գɹ�ȡ����������
 * ���޲ι��죬������������ʱ��ֵʧ�ܡ�
 * @author ����
 *
 */
public class PropertyDemo1 {
	
	private String usersName;//����Ӹ�s������xml�����������޹�
	
	//ע����Ϊ�չ���һ�����ɾͻ���ʾsyso��������bean1.xml�����ļ�����ʱ����ʾ�����ݣ�
	//�����ǲ����ڲ�����������
	private PropertyDemo1() {
		System.out.println("hello~�ȼ����޲ι������");
	}
	
	private PropertyDemo1(String userName) {//���������������ı䣬��Ӱ��xml����
		this();
		this.usersName = userName;
	}
	
	public void test1() {
		System.out.println("Demo1..."+usersName);
	}
}
