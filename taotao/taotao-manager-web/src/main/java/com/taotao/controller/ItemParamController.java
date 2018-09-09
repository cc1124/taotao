package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
     private ItemParamService itemParamService;
    @RequestMapping("/query/itemcatid/{itemCatId}")
     @ResponseBody
     public TaotaoResult getItemParamById(@PathVariable long itemCatId){
        TaotaoResult result = itemParamService.getItemParamById(itemCatId);
        return  result;
    }
    @RequestMapping("/save/{itemCatId}")
    @ResponseBody
    public TaotaoResult addItemParam(@PathVariable Long itemCatId,String paramData){
         TbItemParam tbItemParam = new TbItemParam();
         tbItemParam.setItemcatId(itemCatId);
         tbItemParam.setParamData(paramData);
        TaotaoResult result = itemParamService.addItemParam(tbItemParam);
        return  result;
    }

}
