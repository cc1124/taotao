package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

public interface SearchService {
	/**
	 * 根据当前页面，以及查询条件查询
	 * @param queryString 查询条件
	 * @param page  当前页码
	 * @param rows  每页显示条数
	 * @return  SearchResult 结果
	 */
    public SearchResult search(String queryString, int page, int rows);
}
