package com.zljx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("/page/{html}")
    public String page(@PathVariable("html") String html){
        return html;
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

   /* @RequestMapping("Test01")
    public String index01(){
        return "join";
    }
    @RequestMapping("Test02")
    @ResponseBody
    public String index02(){
        return "hello";
    }

    @RequestMapping("image")
    public String image(){
        return "iamge";
    }*/


}
