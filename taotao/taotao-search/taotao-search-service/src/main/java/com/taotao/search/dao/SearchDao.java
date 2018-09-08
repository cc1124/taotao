package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
@Repository
public class SearchDao {
	/**
	 * 根据业务层传入的条件查询文档库
	 * @param query 查询条件
	 * @return  商品结果集，总记录条数，总页数
	 */
	@Autowired
	private SolrServer solrServer;
	public SearchResult search(SolrQuery query) {
		try {
			//根据query对象查询索引库
			QueryResponse queryResponse = solrServer.query(query);
			//获取商品的列表
			SolrDocumentList documentList = queryResponse.getResults();

			List<SearchItem> itemList  = new ArrayList<>();
			for (SolrDocument solrDocument : documentList) {
				SearchItem sItem = new SearchItem();
				sItem.setId((String) solrDocument.get("id"));
				sItem.setCategoryName((String) solrDocument.get("item_category_name"));
				sItem.setImage((String) solrDocument.get("item_image"));
				sItem.setPrice((long) solrDocument.get("item_price"));
				sItem.setSellPoint((String) solrDocument.get("item_sell_point"));
				
				//取高亮显示
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
				String itemTitle = "";
				//有高亮显示的内容时。
				if (list != null && list.size() > 0) {
					itemTitle = list.get(0);
				} else {
					itemTitle = (String) solrDocument.get("item_title");
				}
				sItem.setTitle(itemTitle);
				//添加到商品列表
				itemList.add(sItem);
			}
			SearchResult result = new SearchResult();
			result.setItemList(itemList);
			result.setRecordCount(documentList.getNumFound());
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
