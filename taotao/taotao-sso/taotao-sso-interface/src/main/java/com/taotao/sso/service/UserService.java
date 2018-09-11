package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
    /**
     * 检测数据是否可用
     * @param param 需要检测的数据
     * @param type 检测的类型  1.用户名 2.手机号码 3.邮箱
     * @return
     */
       TaotaoResult checkDate(String param,int type);

    /**
     * 用户注册  注意要做数据校验
     * @param tbUser  需要注册的用户
     * @return   {status，200 ，msg:"ok",data:""}如果status为400，表示注册不成功，并且返回注册失败
     */
       TaotaoResult createUser(TbUser tbUser);

    /**
     * 用户登录，注意要做数据校验
     * @param userName 用户账号
     * @param passWord 用户密码
     * @return  {status，200 ，msg:"ok",data:"token"}token 不重复的uuid
     */
       TaotaoResult loginUser(String userName,String passWord);

    /**
     * 查询用户是否登录
     * @param token  令牌
     * @return  {status，200 ，msg:"ok",data:"用户对象的json"}注意
     * token要去查redis
     */
       TaotaoResult getUserByToken(String token);
}
