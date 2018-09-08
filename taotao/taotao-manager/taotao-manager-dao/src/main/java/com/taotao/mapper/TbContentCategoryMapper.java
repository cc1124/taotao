package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbContentCategory;

public interface TbContentCategoryMapper {
	/**
	 * 根据商品分类ID查询商品
	 * @param parentId
	 * @return
	 */
	public List<TbContentCategory> getTbContentCateGoryById(long parentId);
	/**
	 * 添加一个新的分类节点
	 * @param tbContentCategory  所要添加的节点的对象
	 */
	public void insertTbContentCategory(TbContentCategory tbContentCategory);
	/**
	 * 根据id查询一个节点的所有信息
	 * @param id
	 * @return
	 */
	public TbContentCategory getTbContentCategory(long id);
	/**
	 * 对节点的修改
	 * @param tbContentCategory
	 */
	public void updateTbContentCategory(TbContentCategory tbContentCategory);

}