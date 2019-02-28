package cn.ssijri.xmlanno;

import javax.annotation.Resource;

/**
 * ���������ļ���ע�⿪����Ϸ�ʽ
 * ���������ϾͲ���Ҫע��@Component֮����
 * @author ����
 *
 */
public class BookService {

	//ʹ��ע��ע������
	//��name��ֵ����xml��bean��id���������������봴���޹أ�
	@Resource(name="bookDao")
	private BookDao bookDao1;
	
	@Resource(name="orderDao")
	private OrderDao orderDao;
	
	public void add() {
		System.out.println("BookService... ...");
		bookDao1.add();
		orderDao.add();
	}
}
