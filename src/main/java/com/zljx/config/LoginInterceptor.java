package com.zljx.config;

import com.zljx.mapper.LoginMapper;
import com.zljx.vo.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginMapper loginMapper;


    @Autowired
    private StringRedisTemplate redistemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       try {
           Cookie[] cookies = request.getCookies();
           String token = "";
           if(cookies!=null){
               for (Cookie cookie:cookies) {
                   if(cookie.getName().equals("ZLXJ")){
                       token = cookie.getValue();
                       break;
                   }
               }
           }

           /*HttpSession session = request.getSession();
           String token = (String) session.getAttribute("userId");*/


           if(!StringUtils.isEmpty(token)){
               //通过jwt解析拿到 userId
               Integer userId = JWTUtil.getUserId(token);
               String username = JWTUtil.getUsername(token);
               if(!JWTUtil.isTokenExpired(token)&&!StringUtils.isEmpty(userId)&&!StringUtils.isEmpty(username)){
                   // 如果没有过期 ，用户进行了操作，则覆盖之前的redis 登陆数据
                   redistemplate.opsForValue().set("session_userId_"+userId,token,60*5*2, TimeUnit.SECONDS);
                   return true;
               }else{
                   // 判断改key是否存在 ，不存在
                   if(StringUtils.isEmpty(redistemplate.opsForValue().get("session_userId_" + userId))){
                       response.setHeader("content-type", "text/html;charset=UTF-8");
                       response.getWriter().write("<script>alert('账号不活跃登陆有效期已失效，请重新登陆。');window.location.href='/login'</script>");
                       return false;
                   }else {
                       String password = loginMapper.selectById(userId).getPassword();
                       System.out.println("password  "+password);
                       String sign = JWTUtil.sign(username, userId, password);
                       //重新获取token  在把新的签名存放在cookie
                       Cookie cookie = new Cookie("ZLXJ",sign);
                       cookie.setMaxAge(24*3600);
                       cookie.setPath("/");
                       response.addCookie(cookie);
                       redistemplate.opsForValue().set("session_userId_"+userId,sign,60*5*2, TimeUnit.SECONDS);
                       return true;
                   }

               }
           }else{
               response.sendRedirect("/login");
               return false;
           }

       }catch (Exception e){
            e.printStackTrace();
       }
        response.sendRedirect("/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
