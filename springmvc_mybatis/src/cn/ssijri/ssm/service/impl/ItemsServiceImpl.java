package cn.ssijri.ssm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ssijri.ssm.mapper.ItemsMapper;
import cn.ssijri.ssm.mapper.ItemsMapperCustom;
import cn.ssijri.ssm.po.Items;
import cn.ssijri.ssm.po.ItemsCustom;
import cn.ssijri.ssm.po.ItemsQueryVo;
import cn.ssijri.ssm.service.ItemsService;

/**
 * ��Ʒ�Ĺ���
 * serviceʵ����
 * @author ����
 *
 */
public class ItemsServiceImpl implements ItemsService{

	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	/**
	 * ��service���뵽dao
	 */
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {
		// ͨ��ItemsMapperCustom��ѯ���ݿ�
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	/**
	 * ����id��ѯ��Ʒ
	 */
	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		
		Items items = itemsMapper.selectByPrimaryKey(id);
		//�м����Ʒ��Ϣ����ҵ����
		//������
		//����ItemsCustom
		ItemsCustom itemsCustom = null;
		//��Items�����ݿ�����ItemsCustom��
		if(items!=null) {
			itemsCustom = new ItemsCustom();
			BeanUtils.copyProperties(items,itemsCustom);
		}
		return itemsCustom;
	}

	/**
	 * ������Ʒ��Ϣ
	 */
	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
		//���ҵ��У�飬ͨ����service�ӿڶԹؼ���������У��
		//У��id�Ƿ�Ϊ�գ������׳��쳣
		/*if(null==id) {
			System.out.println("�������ݵ�idֵ�ǿյģ�������");
		}*/
		//������Ʒ��Ϣʹ��updateByPrimaryKeyWithBLOBs ����items���������ֶΰ������ı��ֶ�
		//updateByPrimaryKeyWithBLOBsҪ����봫��id
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

	
}
