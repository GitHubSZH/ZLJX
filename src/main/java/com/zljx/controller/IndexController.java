package com.zljx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("Test01")
    public String index01(){
        return "join";
    }
    @RequestMapping("Test02")
    @ResponseBody
    public String index02(){
        return "hello";
    }
}
