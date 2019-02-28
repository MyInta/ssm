package cn.ssijri.ssm.service;

import java.util.List;

import cn.ssijri.ssm.po.ItemsCustom;
import cn.ssijri.ssm.po.ItemsQueryVo;

/**
 * 商品管理的service
 * @author 银涛
 *
 */
public interface ItemsService {
	
	//商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
	
	//根据id查询商品信息
	/**
	 * 
	 * @param id 查询商品的id
	 * @return
	 * @throws Exception
	 */
	public ItemsCustom findItemsById(Integer id) throws Exception;
	
	//修改商品信息
	/**
	 * 
	 * @param id修改商品的id
	 * @param itemsCustom 修改的商品信息
	 * @throws Exception
	 */
	public void updateItems(Integer id,ItemsCustom itemsCustom) throws Exception;
}
