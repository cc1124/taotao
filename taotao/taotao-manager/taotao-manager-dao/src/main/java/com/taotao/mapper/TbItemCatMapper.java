package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbItemCat;

public interface TbItemCatMapper {
	
	/**
	 * 根据商品分类parentId查询商品分类
	 * @param parentId 父级目录id
	 * @return
	 */
    public List<TbItemCat> getCatList(long parentId);
}