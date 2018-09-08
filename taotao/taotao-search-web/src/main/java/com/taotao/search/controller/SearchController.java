package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {
	@Value("${ITEM_ROWS}")
    private	int ITEM_ROWS; 
	
 @Autowired
 private SearchService searchService;
 @RequestMapping("/search")
 public  String search(@RequestParam("q")String queryString,
		 @RequestParam(defaultValue="1")Integer page,Model model) throws Exception{
	 //商品集合，总记录条数，
	 queryString = new String(queryString.getBytes("iso-8859-1"),"utf-8");
	  SearchResult result = searchService.search(queryString, page, ITEM_ROWS);
	  model.addAttribute("query", queryString);
	  model.addAttribute("totalPages",result.getPageCount());
	  model.addAttribute("itemList", result.getItemList());
	  model.addAttribute("page", page);
	  return "search";
 }
}
