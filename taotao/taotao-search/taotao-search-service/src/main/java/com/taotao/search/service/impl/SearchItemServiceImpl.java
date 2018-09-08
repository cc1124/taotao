package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.springframework.stereotype.Service;

@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SolrServer solrServer;
	@Override
	public TaotaoResult importAllItems() {
		try {
			List<SearchItem> itemList = searchItemMapper.getItemList();
			for (SearchItem searchItem : itemList) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", searchItem.getId());
				doc.addField("item_title", searchItem.getTitle());
				doc.addField("item_sell_point", searchItem.getSellPoint());
				doc.addField("item_price", searchItem.getPrice());
				doc.addField("item_image", searchItem.getImage());
				doc.addField("item_category_name", searchItem.getCategoryName());
				doc.addField("item_desc", searchItem.getItemDesc());
				solrServer.add(doc);
			}
			solrServer.commit();
			return TaotaoResult.build(200, "成功导入");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TaotaoResult.build(500, "出现错误！");
	}

	@Override
	public TaotaoResult addDocument(long itemId) {
		SearchItem searchItem = searchItemMapper.getItemById(itemId);
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSellPoint());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategoryName());
		document.addField("item_desc", searchItem.getItemDesc());
		try {
			solrServer.add(document);
			solrServer.commit();
			return TaotaoResult.ok();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
