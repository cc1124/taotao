package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.jedis.JedisClient;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class ItemServiceImpl implements ItemService {
    @Value("${ITEM_INFO}")
	 private  String ITEM_INFO;
	@Value("${BASE}")
    private  String BASE;
	@Value("${DESC}")
	private  String DESC;
	@Value("${PARAM}")
	private  String PARAM;
    @Autowired
	private JedisClient jedisClient;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private ActiveMQTopic topic;
	@Autowired
	private TbItemMapper  tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public TbItem geTbItemById(long itemid) {
		try {
			String json = jedisClient.get(ITEM_INFO + ":" + itemid + BASE);
			//判断部位null，也不为“”
			if (StringUtils.isNoneBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		//从缓存中取数据
		TbItem tbItem = tbItemMapper.geTbItemById(itemid);
		//把数据库中的数据加入缓存
		try {
			jedisClient.set(ITEM_INFO+":"+itemid+BASE, JsonUtils.objectToJson(tbItem));
		}catch (Exception e){
            e.printStackTrace();
		}
	return tbItem;
	}
	@Override
	public EasyUIDataGridResult getTbItemsList(int page, int rows) {
		//分页插件
		PageHelper.startPage(page, rows);
		
		List<TbItem> itemsList = tbItemMapper.getTbItemsList();
		PageInfo<TbItem> info =new PageInfo<>(itemsList);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(info.getTotal());
		result.setRows(itemsList);
		return result;
	}
	
	@Override
	public TaotaoResult insertTbItem(TbItem tbItem,String desc) {
		//页面传过来的数据没有id， status，created，updated
		final long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		Date date = new Date();
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		tbItemMapper.insertTbItem(tbItem);

		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		tbItemDescMapper.insertTbItemDesc(tbItemDesc);

		/**
		 * 在这里发布消息，更新缓存
		 * 1.用点对点 还是订阅发布
		 * 2.发送过去的数据是什么对象的数据
		 *    只发送id
		 *    只发送json
		 */
         jmsTemplate.send(topic, new MessageCreator() {
			 @Override
			 public Message createMessage(Session session) throws JMSException {
				  TextMessage textMessage = session.createTextMessage(itemId+"");
				  return textMessage;
			 }
		 });

		return TaotaoResult.build(200, "保存商品成功");
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		try {
			String json = jedisClient.get(ITEM_INFO + ":" + itemId + DESC);
			//判断部位null，也不为“”
			if (StringUtils.isNoneBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		   TbItemDesc tbItemDesc = tbItemDescMapper.getItemDescById(itemId);
		try {
			jedisClient.set(ITEM_INFO+":"+itemId+DESC, JsonUtils.objectToJson(tbItemDesc));
		}catch (Exception e){
			e.printStackTrace();
		}
		return tbItemDesc;
	}
}
