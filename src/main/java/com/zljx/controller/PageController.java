package com.zljx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
//@RequestMapping("/manage")
public class PageController {

    /**后台登录页面*/
    @RequestMapping("/login")
    public String doLogin(){
        return "managePage/login";
    }


    @Autowired
    private StringRedisTemplate redistemplate;

    @RequestMapping("/test01")
    @ResponseBody
    public String test01(){
        //ShardedJedis redis = RedisAutoConfiguration.redis();
        //redis.set("xhm","ydnp");
        //return redis.get("xhm");
        // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
       //redis.set("sssss", "6666", "XX", "PX", 1000*60);
        redistemplate.opsForValue().set("ssss","sss",60, TimeUnit.SECONDS);
        System.out.println(redistemplate.opsForValue().get("ssss"));

        redistemplate.opsForValue().set("ssss","sss111",60, TimeUnit.SECONDS);

        System.out.println(redistemplate.opsForValue().get("ssss"));

        return "susess";

    }

    @RequestMapping("/test02")
    @ResponseBody
    public String test02(){
        System.out.println(redistemplate.opsForValue().get("ssss"));

        return "susess";

    }

    @RequestMapping("/test03")
    @ResponseBody
    public String test03(){
       return "sss";

    }







    /**登录后的页面*/
   /* @RequestMapping("/main")
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
    }*/

}
