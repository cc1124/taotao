package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

import java.io.Serializable;

public class Item  extends TbItem implements Serializable {

       public  Item(){

       }
       //外界查询数据库吧数据库中的数据装载到Tbitem 对象里面，我们要把Tbitem对象的属性复制到Item属性里面去
       public  Item(TbItem tbItem){
           this.setBarcode(tbItem.getBarcode());
           this.setCid(tbItem.getCid());
           this.setCreated(tbItem.getCreated());
           this.setId(tbItem.getId());
           this.setImage(tbItem.getImage());
           this.setNum(tbItem.getNum());
           this.setPrice(tbItem.getPrice());
           this.setSellPoint(tbItem.getSellPoint());
           this.setStatus(tbItem.getStatus());
           this.setTitle(tbItem.getTitle());
           this.setUpdated(tbItem.getUpdated());

       }

        public  String[] getImages() {
            if (this.getImage() != null && !"".equals(this.getImage())) {
                String[] strings = this.getImage().split(",");
                return strings;
            }
            return  null;
        }
}
