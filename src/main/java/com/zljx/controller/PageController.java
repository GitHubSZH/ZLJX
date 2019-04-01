package com.zljx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/manage")
public class PageController {

    /**后台登录页面*/
    @RequestMapping("/login")
    public String doLogin(){
        return "managePage/login";
    }

    /**登录后的页面*/
    @RequestMapping("/main")
    public String doMain(){
        return "managePage/manageMain";
    }

    @RequestMapping("/doCartManage")
    public String dodoCartManage(){
        return "managePage/cartManage";
    }

}
