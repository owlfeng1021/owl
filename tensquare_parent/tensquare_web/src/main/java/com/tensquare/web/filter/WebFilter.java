package com.tensquare.web.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.protocol.RequestContent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
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
        // 这里的RequestContext 是 zuul 内部的 context
        RequestContext currentContext = RequestContext.getCurrentContext();
        //
        HttpServletRequest request = currentContext.getRequest();

        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        if (authorization!=null && !"".equals(authorization)){
            // 把头信息继续向下传
            currentContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }
}
