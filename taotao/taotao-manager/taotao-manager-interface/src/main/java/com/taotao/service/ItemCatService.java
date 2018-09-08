package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	/**
	 * 调用数据库查询父级下的商品分类信息
	 * @param parentId  父级目录对象
	 * @return
	 */
      public List<EasyUITreeNode> getCatList(long parentId);
}
