package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	/**
	 * 根据商品id查询商品信息
	 * @param itemid 商品id
	 * @return 
	 */
    TbItem geTbItemById(long itemid);
    /**
     * 根据页面传过来的参数分页显示商品信息
     * @param page 当前页
     * @param rows 每页显示的条数
     * @return
     */
     EasyUIDataGridResult getTbItemsList(int page, int rows);
    /**
     * 添加商品到数据库
     * @param tbItem
     * @return
     */
     TaotaoResult insertTbItem(TbItem tbItem, String desc);

    /**
     * 根据商品id查询商品的描述信息
     * @param itemId  商品的id
     * @return  指定商品的描述信息
     */
    TbItemDesc getItemDescById(long itemId );

}
