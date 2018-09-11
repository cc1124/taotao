package com.taotao.mapper;


import com.taotao.pojo.TbUser;

public interface TbUserMapper {
    /**
     * 根据用户名查询数据库
     * @param userName 用户名
     * @return  如果反悔对象不为null，表示用户名已经存在
     */
     TbUser getUserByUserName(String userName);

    /**
     * 根据手机号码查询数据库
     * @param phine  手机号
     * @return 如果反悔对象不为null，表示手机号已经存在
     */
     TbUser getUserByPhone(String phine);

    /**
     * 根据Email查询数据库
     * @param email  邮箱
     * @return  如果反悔对象不为null，表示Email已经存在
     */
     TbUser getUserByEmail(String email);

    /**
     * 添加一个用户到数据库
     * @param tbUser  需要添加的用户信息
     */
     void insertUser(TbUser tbUser);

    /**
     * 根据数据账号密码查询用户是否存在，注意密码是MD5加密的
     * @param uaerName  用户账号
     * @param passWord  用户密码
     * @return 如果不为null ，表示用户存在
     */
     TbUser  getUserByUserAndPass(String uaerName,String passWord);
}