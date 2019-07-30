package com.zljx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zljx.config.MySessionContext;
import com.zljx.mapper.LoginMapper;
import com.zljx.pojo.Admin;
import com.zljx.vo.JWTUtil;
import com.zljx.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {


    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private StringRedisTemplate redistemplate;

    @RequestMapping("/doLogin")//doLogin
    public SysResult doLogin(Admin admin, HttpServletRequest request, HttpServletResponse response){
        try {
            SysResult result = new SysResult();
            if(StringUtils.isEmpty(admin.getUsername())||StringUtils.isEmpty(admin.getPassword())){
                return SysResult.build(201,"请账号或者密码填写完整");
            }

            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",admin.getUsername()).eq("password",DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
            Admin adminDB = loginMapper.selectOne(queryWrapper);
            if(adminDB!=null){
                //设置Session
                HttpSession session = request.getSession(true);

                MySessionContext myc= MySessionContext.getInstance();
                myc.addSession(session);

                //使用jtw 创建token 并且设置登陆有效期
                String sign = JWTUtil.sign(adminDB.getUsername(), adminDB.getId(), admin.getPassword());

                System.out.println("sign "+sign);

                adminDB.setPassword("****");
                result.setStatus(200);
                result.setData(sign);

                //设置session
                //session.setAttribute("userId",String.valueOf(adminDB.getId()));

                //设置Cookie
                Cookie cookie = new Cookie("ZLXJ",sign);
                cookie.setMaxAge(24*3600);
                cookie.setPath("/");
                response.addCookie(cookie);

                //SingleLoginFilter.optionMap.put(String.valueOf(adminDB.getId()), SingleLoginFilter.getRequestSessionId((request)));

                //登陆成功之后 往redis里面存放 用户id ： token值  和过期时间
                redistemplate.opsForValue().set("session_userId_"+adminDB.getId(),sign,60*5*2, TimeUnit.SECONDS);

                return result;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"账号或者密码错误！");

    }

    @RequestMapping("/loginOut")//doLogin
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if("ZLXJ".equals(cookie.getName())){
                Cookie newCookie = new Cookie("ZLXJ", "");
                newCookie.setMaxAge(3600);
                newCookie.setPath("/");
                response.addCookie(newCookie);
                break;
            }
        }
        //在清除 session
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        response.sendRedirect("/login");
    }


}
