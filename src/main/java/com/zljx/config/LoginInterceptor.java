package com.zljx.config;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       try {
           Cookie[] cookies = request.getCookies();
           String key = "";
           if(cookies!=null){
               for (Cookie cookie:cookies) {
                   if(cookie.getName().equals("ZLXJ")){
                       key = cookie.getValue();
                       break;
                   }
               }
           }
           HttpSession session = request.getSession();
           String token = (String) session.getAttribute("userId");
           System.out.println("token="+token);
           System.out.println("key="+key);
           if(!StringUtils.isEmpty(key)&&!StringUtils.isEmpty(token)){
               if(token.equals(key)){
                   return true;
               }else{
                   response.sendRedirect("/login");
                   return false;
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
