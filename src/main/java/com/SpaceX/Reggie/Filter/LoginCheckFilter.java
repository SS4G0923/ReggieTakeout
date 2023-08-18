package com.SpaceX.Reggie.Filter;

import com.SpaceX.Reggie.common.BaseContext;
import com.SpaceX.Reggie.common.R;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher(); //路径匹配器
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        //log.info("拦截到请求：{}", requestURI);

        //不需要处理的请求路径
        String[] urls = new String[]{
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**",
          "/common/**",
          "/user/sendMsg",
          "/user/login"
        };

        boolean check = check(urls, requestURI);

        if(check){
            //log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户{}已登录", request.getSession().getAttribute("employee"));
            BaseContext.setCurrentId((Long)request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("user")!=null){
            log.info("用户{}已登录", request.getSession().getAttribute("user"));
            BaseContext.setCurrentId((Long)request.getSession().getAttribute("user"));
            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
