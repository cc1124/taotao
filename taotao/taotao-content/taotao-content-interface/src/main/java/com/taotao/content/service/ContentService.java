package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	/**
	 * 查询类别下的所有
	 * @param categoryId
	 * @return
	 */
    public EasyUIDataGridResult findContentAll(long categoryId);
    /**
     * 添加一个信息
     * @param tbContent
     * @return taotaoresult类型的结果
     */
    public TaotaoResult addContent(TbContent tbContent);
    /**
     * 查询数据库得到这个分类的指定的内容
     * @param categoryId 分类ID
     * @return
     */
    public List<TbContent> findContent(long categoryId);
}
