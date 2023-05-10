package com.kay.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kay.common.Result;
import com.kay.common.StatusCode;
import com.kay.utils.JwtUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //获取token
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        response.setContentType("text/JavaScript;charset=utf-8");
        //验证token
        if (Objects.isNull(token) || "".equals(token.trim())) {
            try {
                PrintWriter out = response.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                String s = objectMapper.writeValueAsString(new Result(StatusCode.unauthorized, "请先登录！"));
                out.print(s);
                out.close();
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            //验证token是否有效
            try {
                JwtUtils.verifyToken(token);
            } catch (TokenExpiredException e) {
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String s = null;
                try {
                    s = objectMapper.writeValueAsString(new Result(StatusCode.expire, "登录过期,请重新登录！"));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                out.print(s);
                out.close();
                return false;
            }
            return true;
        }
    }
}
