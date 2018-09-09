package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

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
     * 添加商品信息
     * @param tbItem  需要添加的商品对象信息
     * @param desc  商品的描述信息
     * @param itemParams 商品的规格参数
     * @return
     */
     TaotaoResult insertTbItem(TbItem tbItem, String desc,String itemParams);

    /**
     * 根据商品id查询商品的描述信息
     * @param itemId  商品的id
     * @return  指定商品的描述信息
     */
    TbItemDesc getItemDescById(long itemId );

    /**
     * 根据商品id查询商品的规格信息并把规格参数转为成为html页面，通过model添加到域里面，在jsp页面展示
     * @param itemId 商品id
     * @return  html页面里面填充了查询规格参数
     */
    String getItemParamItemByItemId(long itemId);
}
