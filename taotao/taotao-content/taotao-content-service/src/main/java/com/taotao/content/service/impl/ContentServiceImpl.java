package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
@Service
public class ContentServiceImpl implements ContentService {
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	@Autowired
	private JedisClient jedisClient;
    @Autowired 
    private TbContentMapper tbContentMapper;
	@Override 
	public EasyUIDataGridResult findContentAll(long categoryId) {
		List<TbContent> contentAll = tbContentMapper.findContentAll(categoryId);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((long) contentAll.size());
		result.setRows(contentAll);
		return result;
	}
	@Override
	public TaotaoResult addContent(TbContent tbContent) {
		
		Date date= new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		
		tbContentMapper.addContent(tbContent);
		jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId()+"");
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> findContent(long categoryId) {
		//这里取缓存
		try {
			String json = jedisClient.hget(CONTENT_KEY, categoryId+"");
			if(StringUtils.isNotBlank(json)){
			  List<TbContent> result = JsonUtils.jsonToList(json, TbContent.class);
			  return result;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/**
		 * 第一次请求，由于没有缓存，所以直接查询数据库，查询完数据库以后
		 * 在return这几千把数据库里面的数据加到redis缓存中。第二次访问的时候
		 * 由于先走上面的代码，先从缓存里面取，取到直接return，
		 * 没有取到代码直接往下执行
		 */
		
		List<TbContent> result = tbContentMapper.findContentAll(categoryId);
		//这里加缓存  把list集合变成json数据
		
		try {
			jedisClient.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
