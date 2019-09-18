package com.zljx.config;

import com.zljx.pojo.Admin;
import com.zljx.vo.ObjectMapperUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: sunzhihan
 * @Date：2019/07/24 8:56
 * @Version 1.0
 **/
//@Component
public class SingleLoginFilter implements Filter {

   // private static final Log log = LogFactory.getLog(SingleLoginFilter.class);
    public static Map<String, String> optionMap = new HashMap<String, String>();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        String userSession = (String) session.getAttribute("userId");
        //用户为空不拦截
        if (userSession==null) {
            filterChain.doFilter(request, response);
            return;
        }
        //查看session中是否存在user的登录信息
        String sessionId = getRequestSessionId((HttpServletRequest)request);
        if(sessionId!=null&&sessionId.equals(optionMap.get(userSession))) {
            filterChain.doFilter(request, response);
        } else {
            //页面提示登录失效或您的账号已在其它地点登录
            //log.info("用户账号 "+userId+" 被强制挤下线,sessionId为:"+sessionId);
            //System.out.println("------------\"用户账号 \"+userId+\" 被强制挤下线,sessionId为:\"+sessionId------------");
            ((HttpServletResponse) response).setHeader("content-type", "text/html;charset=UTF-8");
              session.removeAttribute("userId");
              response.getWriter().write("<script>alert('您的账号已在其它地点登录');window.location.href='/login'</script>");
        }
    }

    public static String getRequestSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName()!=null&&cookie.getName().startsWith("JSESSIONID")) {
                return cookie.getValue();
            }
        }
        return null;
    }
    @Override
    public void destroy() {
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
