package cn.ssijri.ssm.service;

import java.util.List;

import cn.ssijri.ssm.po.ItemsCustom;
import cn.ssijri.ssm.po.ItemsQueryVo;

/**
 * ��Ʒ�����service
 * @author ����
 *
 */
public interface ItemsService {
	
	//��Ʒ��ѯ�б�
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
	
	//����id��ѯ��Ʒ��Ϣ
	/**
	 * 
	 * @param id ��ѯ��Ʒ��id
	 * @return
	 * @throws Exception
	 */
	public ItemsCustom findItemsById(Integer id) throws Exception;
	
	//�޸���Ʒ��Ϣ
	/**
	 * 
	 * @param id�޸���Ʒ��id
	 * @param itemsCustom �޸ĵ���Ʒ��Ϣ
	 * @throws Exception
	 */
	public void updateItems(Integer id,ItemsCustom itemsCustom) throws Exception;
}
