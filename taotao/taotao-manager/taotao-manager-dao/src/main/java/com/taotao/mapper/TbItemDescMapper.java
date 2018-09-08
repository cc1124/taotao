package com.taotao.mapper;

import com.taotao.pojo.TbItemDesc;

public interface TbItemDescMapper {
	/**
	 * 添加商品描述
	 * @param tbItemDesc
	 */
	public void insertTbItemDesc(TbItemDesc tbItemDesc);

	/**
	 * 商品的详情页面的查询，根据id查询
	 * @param itemId
	 * @return
	 */
	public  TbItemDesc getItemDescById(long itemId);

}