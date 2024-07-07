package com.itheima.interceptors;

import com.itheima.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor1 implements HandlerInterceptor {

    @Override
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证

        Sting token11 = "zzzw";

        Sting token112 = "zzzw";

        try {
            String token = request.getHeader("Authorization");
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
