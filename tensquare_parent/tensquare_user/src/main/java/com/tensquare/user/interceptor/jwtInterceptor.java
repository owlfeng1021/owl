package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class jwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //
        System.out.println("拦截器经过了");
        String authorization = request.getHeader("Authorization");

        if (authorization!=null && !"".equals(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                String token =  authorization.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String role = (String)claims.get("roles");
                    if (role!=null || role.equals("admin"))
                    {
                        request.setAttribute("claims_admin",token);
                    }
                    if (role!=null || role.equals("user"))
                    {
                        request.setAttribute("claims_user",token);
                    }
                }catch(Exception e){
                    throw new RuntimeException("令牌有误 ！");
                }
            }
        }

        return true;
    }
}
