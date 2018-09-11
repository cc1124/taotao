package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkDate(@PathVariable String param ,@PathVariable Integer type){
        TaotaoResult result = userService.checkDate(param, type);
        return result;
    }


}
