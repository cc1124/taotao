package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
	@Override
	public SearchResult search(String queryString, int page, int rows)  {
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置默认搜索域
		query.set("df", "item_keywords");
		//设置每页的开始
		query.setStart(0);
		//设置每页显示的条数
		query.setRows(rows);
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<span style ='color:red' >");
		query.setHighlightSimplePost("</span>");
		SearchResult result = searchDao.search(query);
		//计算总页数
		long pageCount =  result.getRecordCount()%rows==0?result.getRecordCount()/rows:result.getRecordCount()/rows+1;
		result.setPageCount(pageCount);
		return result;
	}

}
