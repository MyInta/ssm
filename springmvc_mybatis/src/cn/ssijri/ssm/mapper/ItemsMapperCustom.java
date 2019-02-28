package cn.ssijri.ssm.mapper;

import java.util.List;
import cn.ssijri.ssm.po.ItemsCustom;
import cn.ssijri.ssm.po.ItemsQueryVo;

public interface ItemsMapperCustom {
    
	//商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
}