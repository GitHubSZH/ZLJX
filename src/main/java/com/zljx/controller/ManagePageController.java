package com.zljx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManagePageController {

    /**登录后的页面*/
    @RequestMapping("/main")
    public String doMain(){
        return "managePage/manageMain";
    }

    @RequestMapping("/doCartManage")
    public String doCartManage(){
        return "managePage/cartManage";
    }

    @RequestMapping("/doCartAdd")
    public String doCartAdd(){
        return "managePage/cartAdd";
    }

    @RequestMapping("/doUpdateCart")
    public String doUpdateCart(){
        return "managePage/cartUpdate";
    }

    @RequestMapping("/{workPage}")
    public String workPage(@PathVariable String workPage){
        return "managePage/"+workPage;
    }
}
