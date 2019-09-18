package com.zljx.websocket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zljx.mapper.MessageUserMapper;
import com.zljx.pojo.MessageUser;
import com.zljx.vo.ClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: sunzhihan
 * @Date：2019/07/17 14:05
 * @Version 1.0
 **/
@Controller
@RequestMapping("manage/")
public class webSocketManageController {


    @Autowired
    private SocketServer socketServer;

    @Autowired
    private MessageUserMapper messageUserMapper;


    /**
     *
     * 服务端页面
     *
     * @return
     */
    @RequestMapping(value = "admin")
    public String admin() {


        return "admin";
    }

    /**
     * @describe  获取在线人数，和用户名称
     * @params []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "getUsersList")
    @ResponseBody
    public Map<String,Object> getUsersList(){
        Map<String,Object> map = new HashMap<>();

        // 获取当前连接数量
        int num = socketServer.getOnlineNum();
        // 获取当前连接的用户
        List<String> list = socketServer.getOnlineUsers();

        map.put("num", num);

        map.put("users", list);
        return map;
    }

    /**
     * 个人信息推送
     *
     * @return
     */
    @RequestMapping("sendmsg")
    @ResponseBody
    public String sendmsg(String msg, String username) {
        // 第一个参数 :msg 发送的信息内容
        // 第二个参数为用户长连接传的用户人数
        System.out.println(username);
        SocketServer.sendMessage(msg,username);
        return "success";
    }


}
