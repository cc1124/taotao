package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface TbContentMapper {
	/**
	 * 查询分类的所有信息
	 * @param categoryId 分类ID
	 * @return
	 */
	public List<TbContent> findContentAll(long categoryId);
	/**
	 * 添加一个种类的广告到数据库
	 * @param tbContent  广告的信息
	 */
	public void addContent(TbContent tbContent);
 
}