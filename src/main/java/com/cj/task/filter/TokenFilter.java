package com.cj.task.filter;

import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cj on 2018/8/28.
 */
//@WebFilter(filterName = "TokenFilter", urlPatterns = {"/*"})
public class TokenFilter implements Filter {
    @Autowired
    UserService userService;
    String[] includeUrls = new String[]{"/register", "/login"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        String url = request.getRequestURI();
        if (!isNeedFilter(url)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                response.setStatus(401);
                response.getWriter().println("{\"status\":401,\"msg\":\"token为空！\",\"data\":null}");
                return;
            }
            System.out.println("从head获取token " + token);
            Result result = userService.findUserByToken(token);
            if (result.getData() == null) {
                response.getWriter().println("{\"status\":401,\"msg\":\"token校验失败！\",\"data\":null}");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    public boolean isNeedFilter(String url) {
        for (String includeUrl : includeUrls) {
            if (url.contains(includeUrl)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
