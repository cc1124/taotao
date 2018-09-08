package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;

@Controller
public class PageController {
	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;

	
	@RequestMapping("/index")
    public String showIndex(Model model){
		List<TbContent> contents = contentService.findContent(AD1_CID);
		List<Ad1Node> result = new ArrayList<>();
		for (TbContent tbContent : contents) {
			Ad1Node node = new Ad1Node();
			//大图
			node.setSrcB(tbContent.getPic());
			//图片的高度
			node.setHeight(AD1_HEIGHT);
			//图片的描述
			node.setAlt(tbContent.getTitleDesc());
			//设置宽度
			node.setWidth(AD1_WIDTH);
			//小图
			node.setSrc(tbContent.getPic2());
			//大图的宽度
			node.setWidthB(AD1_WIDTH_B);
			//图片的链接
			node.setHref(tbContent.getUrl());
			//大图的高度
			node.setHeightB(AD1_HEIGHT_B);
            result.add(node);			
		}
		model.addAttribute("ad1",JsonUtils.objectToJson(result));
    	return "index";
    }
}
