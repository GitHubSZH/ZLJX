package com.zljx.controller;

import com.zljx.config.MySessionContext;
import com.zljx.pojo.Admin;
import com.zljx.vo.HttpClientService;
import com.zljx.vo.ObjectMapperUtil;
import com.zljx.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private HttpClientService httpClientService;


    @RequestMapping("/doLogin")//doLogin
    public SysResult doLogin(Admin admin, HttpServletRequest request, HttpServletResponse response){
      /* try {
          Admin result  = manageService.doLogin(admin);
           return SysResult.oK(result);
       }catch (Exception e){
           e.printStackTrace();
       }
       return SysResult.build(201,"账号或者密码错误！");*/
        try {
            String url = "http://localhost:8081/login/doLogin";
            Map<String,String> params = new HashMap<>();
            params.put("username",admin.getUsername());
            params.put("password",DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
            String json = httpClientService.doPost(url, params);
            SysResult result = ObjectMapperUtil.toObejct(json, SysResult.class);
            //设置Session
            if(result.getStatus()==200){



                HttpSession session = request.getSession(true);

                MySessionContext myc= MySessionContext.getInstance();
                myc.addSession(session);


                String toJSON = ObjectMapperUtil.toJSON(result.getData());
                //获取id
                Admin admin1 = ObjectMapperUtil.toObejct(toJSON, Admin.class);
                Integer adminId = admin1.getId();
                String token = DigestUtils.md5DigestAsHex(toJSON.getBytes());
                session.setAttribute("userId",token);

                //根据用户id获取是否SessionId
                String sessionDB_Id = findSessionId(adminId);
               /* if(!StringUtils.isEmpty(sessionDB_Id)){
                    session.invalidate();
                }else {
                    //添加SessionId到数据库
                    //System.out.println("前台sessionId  "+session.getId());
                    String sessionId =  session.getId();
                    save(sessionId,adminId);

                }*/
                //设置Cookie
                Cookie cookie = new Cookie("ZLXJ", token);
                cookie.setMaxAge(7*24*3600);
                cookie.setPath("/");
                response.addCookie(cookie);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"账号或者密码错误！");

    }

    private String findSessionId(Integer adminId) {
        String url = "http://localhost:8081/login/findSessionId";
        Map<String,String> params = new HashMap<>();
        params.put("id",String.valueOf(adminId));
        String json = httpClientService.doPost(url, params);
        SysResult result = ObjectMapperUtil.toObejct(json, SysResult.class);
        return (String) result.getData();
    }

    @Transactional
    public void save(String sessionId,Integer adminId) {
        String url = "http://localhost:8081/login/saveSession";
        Map<String,String> params = new HashMap<>();
        params.put("id",String.valueOf(adminId));
        params.put("sessionId",sessionId);
       httpClientService.doPost(url, params);
    }

}
