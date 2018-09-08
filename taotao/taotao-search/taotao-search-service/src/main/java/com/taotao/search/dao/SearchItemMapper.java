package com.taotao.search.dao;

import java.util.List;
import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
   /**
    * 查询数据库，等到结果集
    * @return
    */
	public List<SearchItem> getItemList();

	public  SearchItem getItemById(Long itemId);
}
