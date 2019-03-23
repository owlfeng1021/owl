package com.tensquare.manager.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;
    /**
     *  在请求pre或者后post执行
     * @return
     */
    @Override
    public String filterType() {
       return "pre"; //在运行之前过滤
//     return "post" //在运行之后过滤
    }

    @Override
    public int filterOrder() {
        return 0;//数字越小就先执行
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *  过滤器内执行的操作 return 任何object的值都将继续执行
     *  setsendzullResponse(false)表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了后台过滤器了 ！");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        if (request.getMethod().equals("OPTIONS")){
            return null;
        }
        if (request.getRequestURI().indexOf("login")>0){
            return null;
        }
        if (authorization!=null && !"".equals(authorization))
        {
            if (authorization.startsWith("Bearer")){
                String substring = authorization.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(substring);
                    String roles = (String) claims.get("roles");
                    if (roles.equals("admin")){
                        currentContext.addZuulResponseHeader("Authorization",authorization);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false);// 停止运行
                }
            }

        }
        currentContext.setSendZuulResponse(false); //停止运行
        currentContext.setResponseStatusCode(404);
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
