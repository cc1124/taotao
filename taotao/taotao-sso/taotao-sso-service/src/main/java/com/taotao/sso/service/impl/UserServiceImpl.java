package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.sso.service.jedis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.DigestException;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Value("${USER_INFO}")
    private  String USER_INFO;
    @Value("${SESSION_EXPIRE}")
    private  int SESSION_EXPIRE;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
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

    @Override
    public TaotaoResult createUser(TbUser tbUser) {
        TaotaoResult result;
        //校验账号是否为空
        if (StringUtils.isBlank(tbUser.getUserName())){
            return  TaotaoResult.build(400,"用户名不能为空！");
        }
        //校验密码是否为空
        if (StringUtils.isBlank(tbUser.getPassWord())){
            return  TaotaoResult.build(400,"密码不能为空！");
        }
        //校验手机号是否为空
        if (StringUtils.isBlank(tbUser.getPhone())){
            return  TaotaoResult.build(400,"手机号码不能为空！");
        }
        //校验Email是否为空
        if (StringUtils.isBlank(tbUser.getEmail())){
            return  TaotaoResult.build(400,"邮箱不能为空！");
        }
        //验证账号是否重复
        result = checkDate(tbUser.getUserName(), 1);
        if (!(boolean)result.getData()){
            return  TaotaoResult.build(400,"账号已存在");
        }
        //校验手机号是否重复
        result= checkDate(tbUser.getPhone(),2);
        if (!(boolean)result.getData()){
            return  TaotaoResult.build(400,"此手机号码已存在");
        }
        //校验邮箱是否重复
        result= checkDate(tbUser.getEmail(),3);
        if (!(boolean)result.getData()){
            return  TaotaoResult.build(400,"此邮箱已存在");
        }
        //补全TbUser其他属性
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        //密码要MD5加密
        String digestPass = DigestUtils.md5DigestAsHex(tbUser.getPassWord().getBytes());
        tbUser.setPassWord(digestPass);
        tbUserMapper.insertUser(tbUser);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult loginUser(String userName, String passWord) {
        //校验账号是否为空
        //校验账号是否为空
        if (StringUtils.isBlank(userName)){
            return  TaotaoResult.build(400,"用户名不能为空！");
        }
        //校验密码是否为空
        if (StringUtils.isBlank(passWord)){
            return  TaotaoResult.build(400,"密码不能为空！");
        }
        //注意密码要MD5加密
        TbUser user = tbUserMapper.getUserByUserAndPass(userName, DigestUtils.md5DigestAsHex(passWord.getBytes()));
        if (user == null){
            return  TaotaoResult.build(400,"账号密码有误，请重新输入");
        }
        String uuid = UUID.randomUUID().toString();
        //生成token
        String token = uuid.replace("-","");
         //注意密码不能存入进去
        user.setPassWord(null);
        //存入redis
        jedisClient.set(USER_INFO+":"+token, JsonUtils.objectToJson(user));
        //设置过期时间
        jedisClient.expire(USER_INFO+":"+token,SESSION_EXPIRE);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_INFO + ":" + token);
        //如果直接这样返回，那么 他会认为你这个东西是string字符串变成json 会自动加上转义符
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(tbUser);
    }
}
