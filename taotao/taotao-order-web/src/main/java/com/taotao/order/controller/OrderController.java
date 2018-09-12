package com.taotao.order.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Value("${TT_CART}")
     private String TT_CART;
    @RequestMapping("/order-cart")
     public String showOrderCart(HttpServletRequest request){
          /*
            1.点击订单的时候，是要是登录状态，所以能够进入这个方法的时候一定经历了拦截器判断是否登录
            2.能够进来表示一定登陆了，那么一定有用户id
            3.用户id应该对应一张表（地址表）但是呢我们没有这张表，所以直接写死
            4.我们要从cookie或者redis中去商品信息
            5.把取到的数据存入域里面
            6.跳转页面
           */
        TbItem user = (TbItem) request.getAttribute("user");
        //根据ID查询用户的地址
        System.out.println(user);
        List<TbItem> cartList = getCartList(request);
        request.setAttribute("cartList",cartList);
        return "order-cart";
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
