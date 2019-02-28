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
 * 商品的管理
 * service实现类
 * @author 银涛
 *
 */
public class ItemsServiceImpl implements ItemsService{

	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	/**
	 * 从service传入到dao
	 */
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {
		// 通过ItemsMapperCustom查询数据库
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	/**
	 * 根据id查询商品
	 */
	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		
		Items items = itemsMapper.selectByPrimaryKey(id);
		//中间对商品信息进行业务处理
		//。。。
		//返回ItemsCustom
		ItemsCustom itemsCustom = null;
		//将Items的内容拷贝到ItemsCustom中
		if(items!=null) {
			itemsCustom = new ItemsCustom();
			BeanUtils.copyProperties(items,itemsCustom);
		}
		return itemsCustom;
	}

	/**
	 * 更新商品信息
	 */
	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
		//添加业务校验，通常在service接口对关键参数进行校验
		//校验id是否为空，若空抛出异常
		/*if(null==id) {
			System.out.println("更新数据的id值是空的！！！！");
		}*/
		//更新商品信息使用updateByPrimaryKeyWithBLOBs 更新items表中所有字段包括大文本字段
		//updateByPrimaryKeyWithBLOBs要求必须传入id
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

	
}
