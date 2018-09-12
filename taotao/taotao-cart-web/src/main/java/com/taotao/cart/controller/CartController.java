package com.taotao.cart.controller;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
     @Value("${TT_CART}")
     private  String TT_CART;
     @Value("${CART_EXPIRE}")
     private int CART_EXPIRE;
     boolean flag = false;
     @Autowired
     private ItemService itemService;

    @RequestMapping("/add/{itemId}")
    public  String addItemCart(@PathVariable Long itemId, int num , HttpServletRequest request, HttpServletResponse response){
        List<TbItem> list = getCartList(request);
        for (TbItem tbItem: list) {
            //他们都是对象类型的整形，把对象类型转变为基本数据类型
            if (tbItem.getId() == itemId.longValue()) {
                //在数据库中num表示的是库存
                tbItem.setNum(tbItem.getNum() + num);
                flag = true;
                break;
            }
        }
              //flag为假代表没有在cookie里面找到相同的商品
              if (!flag){
                  TbItem item = itemService.geTbItemById(itemId);
                  //把页面想要的购买的数量添加进去
                  item.setNum(num);
                  //在添加商品的时候，图片是有多张的，他们之间是以，分割的
                  String images = item.getImage();
                  if (StringUtils.isNotBlank(images)){
                      String image = images.split(",")[0];
                      item.setImage(image);
                  }
                  list.add(item);
              }
              //集合里面肯定有值了 并且集合肯定有值
              CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_EXPIRE,true);
              //存到cookie ，跳转到添加成功页面
              return  "cartSuccess";
        }
        @RequestMapping("/cart")
        public String showCartList(HttpServletRequest request){
            List<TbItem> list = getCartList(request);
            request.setAttribute("cartList",list);
            return  "cart";
        }
        @RequestMapping("/update/num/{itemId}/{num}")
        @ResponseBody
        public TaotaoResult updateNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
            List<TbItem> list = getCartList(request);
            for (TbItem tbItem:list){
                if (tbItem.getId()==itemId.longValue()){
                    tbItem.setNum(num);
                    break;
                }
            }
            CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_EXPIRE,true);
            return  TaotaoResult.ok();
        }
         @RequestMapping("/delete/{itemId}")
         public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,HttpServletResponse response){
             List<TbItem> list = getCartList(request);
             for (int i = 0;i<list.size();i++){
                 TbItem tbItem = list.get(i);
                 if (tbItem.getId()==itemId.longValue()){
                     list.remove(tbItem);
                     break;
                 }
             }
             CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_EXPIRE,true);
             return "cart";
         }

    private List<TbItem> getCartList(HttpServletRequest request){
        //默认使用utf-8编码
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        if(StringUtils.isNotBlank(json)){
            //从cookie里面取数据，娶不到直接返回空集合
            //取到返回指定数据
            List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
            return  list;
        }
        return  new ArrayList<TbItem>();
    }

}
