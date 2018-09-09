package com.taotao.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public TaotaoResult getItemParamById(long itemCatId) {
        //
        TbItemParam itemParam = tbItemParamMapper.getItemParamById(itemCatId);
        if (itemParam!=null){
            return  TaotaoResult.ok(itemParam);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addItemParam(TbItemParam tbItemParam) {
        try{
            Date date = new Date();
            tbItemParam.setCreated(date);
            tbItemParam.setUpdated(date);
            tbItemParamMapper.addItemParam(tbItemParam);
            return  TaotaoResult.ok(tbItemParam);
        }catch (Exception e){
                 e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}
