package com.taotao.item.listener;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ItemChangeListener  implements MessageListener{
    @Value("${HTML_OUT_PATH}")
    private  String HTML_OUT_PATH;
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer  configurer;
    private  Writer writer;
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                String itemId = textMessage.getText();
                //商品信息
                TbItem tbItem = itemService.geTbItemById(Long.valueOf(itemId));
                //商品详情信息
                TbItemDesc itemDesc = itemService.getItemDescById(Long.valueOf(itemId));
                //商品的规格信息
                String itemParam = itemService.getItemParamItemByItemId(Long.valueOf(itemId));
                //通过spring的FreeMarkerConfigurer获取configuration，不需要配置版本信息
                Configuration configuration = configurer.getConfiguration();
                //通过configuration  模板名字
                Template template = configuration.getTemplate("item.ftl");

                Map map = new HashMap();
                map.put("item",new Item(tbItem));
                map.put("itemDesc",itemDesc);
                map.put("itemParam",itemParam);
                writer = new FileWriter(new File(HTML_OUT_PATH+itemId+".html"));
                template.process(map,writer);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
            e.printStackTrace();
            }finally {
                if (writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
