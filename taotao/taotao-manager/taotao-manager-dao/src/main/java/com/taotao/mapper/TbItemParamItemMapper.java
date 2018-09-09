package com.taotao.mapper;


import com.taotao.pojo.TbItemParamItem;

public interface TbItemParamItemMapper {
    /**
     * 把规格参数保存到数据库中
     * @param tbItemParamItem  需要保存的规格参数
     *
     */
     void insertTbItemParamItem(TbItemParamItem tbItemParamItem);

    /**
     * 根据商品id查询商品规格信息
     * @param itemId  商品id
     * @return  商品规格信息
     */
    TbItemParamItem getItemParamItemByItemId(long itemId);
}