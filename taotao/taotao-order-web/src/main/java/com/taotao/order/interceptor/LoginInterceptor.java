package com.taotao.order.interceptor;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Value("${TT_TOKEN}")
    private  String TT_TOKEN;
    @Value("${SSO_LOGIN_URL}")
    private  String SSO_LOGIN_URL;

    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        /**
         * controller :类
         * handler    :类里面的方法
         */
        //handler执行之前调用

        String token = CookieUtils.getCookieValue(request, TT_TOKEN);
        if (StringUtils.isBlank(token)){
            String url = request.getRequestURI().toString();
            //跳转到登录界面，并且把当前的url带上
            response.sendRedirect(SSO_LOGIN_URL+"?redirectUrl="+url);
            return false;
        }
        TaotaoResult result = userService.getUserByToken(token);
        if (result.getStatus()==200){
            String url = request.getRequestURI().toString();
            //跳转到登录界面，并且把当前的url带上
            response.sendRedirect(SSO_LOGIN_URL+"?redirectUrl="+url);
            return false;
        }
        //必须把用户给保存起来
        TbUser tbUser = (TbUser) result.getData();
        request.setAttribute("user",tbUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
         //handler执行之后，modelAndView 返回之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
         //handler执行之后，modelAndView 返回之后
    }
}
