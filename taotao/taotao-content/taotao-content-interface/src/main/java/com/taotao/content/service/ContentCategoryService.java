package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {
	/**
	 * 查询节点
	 * @param parentId  根据父节点id查询
	 * @return 
	 */
   public List<EasyUITreeNode> getContentCategoryList(long parentId);
   /**
    * 添加一个新的节点
    * @param tbContentCategory 添加的节点的信息
    * @return  返回一个TaotaoResult
    */
   public TaotaoResult insertContentCategory(long parentId, String name);
}
