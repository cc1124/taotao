package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/item/{itemId}")
    public  String showItemInfo(@PathVariable  long itemId , Model model){
       //商品基本信息
        TbItem tbItem = itemService.geTbItemById(itemId);
        //返回给页面的对象
        Item item = new Item(tbItem);
        //返回给页面的信息
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);

         model.addAttribute("item",item);
         model.addAttribute("itemDesc",tbItemDesc);
        return  "item";
    }

}
