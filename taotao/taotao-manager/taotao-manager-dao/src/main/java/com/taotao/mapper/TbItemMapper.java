package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbItem;

public interface TbItemMapper {
	/**
	 * 根据id查询商品
	 * @param
	 * @return
	 */
	 TbItem geTbItemById(long itemId);
	 /**
	  * 分页查询
	  * @return
	  */
	
	public  List<TbItem> getTbItemsList();
	/**
	 * 添加商品信息
	 * @param tbItem 
	 */
	public void insertTbItem(TbItem tbItem);
}