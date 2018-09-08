package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		List<TbContentCategory> list = tbContentCategoryMapper.getTbContentCateGoryById(parentId);
		List<EasyUITreeNode> result = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		Date date = new Date();
		tbContentCategory.setCreated(date);
		tbContentCategory.setUpdated(date);
		tbContentCategory.setIsParent(false);
		tbContentCategoryMapper.insertTbContentCategory(tbContentCategory);
		
		TbContentCategory parentContent = tbContentCategoryMapper.getTbContentCategory(parentId);
		if (!parentContent.getIsParent()) {
              TbContentCategory category = new TbContentCategory();
              category.setIsParent(true);
              category.setId(parentId);
              tbContentCategoryMapper.updateTbContentCategory(category);
		}
		return TaotaoResult.ok(tbContentCategory);
	}
	

}
