package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface UserService {
    /**
     * 检测数据是否可用
     * @param param 需要检测的数据
     * @param type 检测的类型  1.用户名 2.手机号码 3.邮箱
     * @return
     */
       TaotaoResult checkDate(String param,int type);
}
