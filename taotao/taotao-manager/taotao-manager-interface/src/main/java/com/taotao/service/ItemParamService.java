package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
    /**
     * 根据分类ID查询制定分类下面是否有规格参数模板存在
     * @param eId  分类ID
     * @return   200表示该分类有规格参数模板，负责返回OK
     */
    TaotaoResult  getItemParamById(long eId);

    /**
     * 保存模板json数据到数据库中的模板表中
     * @param tbItemParam  需要保存的模板json数据
     * @return  200则表示成功  否则返回ok
     */
    TaotaoResult  addItemParam(TbItemParam tbItemParam);
}
