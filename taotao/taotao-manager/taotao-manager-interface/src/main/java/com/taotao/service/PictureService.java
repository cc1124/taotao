package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;

public interface PictureService {
/**
 * 
 * @param bytes 需要上传的图片的字节数组
 * @param name  上传的图片的名字
 * @return
 */
	public PictureResult uploadFile(byte[] bytes, String name);
}
