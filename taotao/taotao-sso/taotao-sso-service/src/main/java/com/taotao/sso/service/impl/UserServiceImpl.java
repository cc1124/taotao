package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public TaotaoResult checkDate(String param, int type) {
        if (type==1){
            TbUser user = tbUserMapper.getUserByUserName(param);
            if (user!=null ){
                return  TaotaoResult.ok(false);
            }
        }else if (type==2){
            TbUser user = tbUserMapper.getUserByPhone(param);
            if (user!=null){
                return  TaotaoResult.ok(false);
            }
        }else if (type==3){
            TbUser user = tbUserMapper.getUserByEmail(param);
            if (user!=null){
                return  TaotaoResult.ok(false);
            }
        }else{
              return  TaotaoResult.build(500,"ok","传入的参数类型不合法");
        }
        return TaotaoResult.ok(true);
    }
}
