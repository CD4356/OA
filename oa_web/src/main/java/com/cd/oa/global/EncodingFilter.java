package com.cd.oa.global;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encoding = "utf-8";

    //获取web.xml容器中配置的filter过滤器的初始化参数
    public void init(FilterConfig filterConfig) throws ServletException {

        if(filterConfig.getInitParameter("encoding")!=null){
            encoding = filterConfig.getInitParameter("encoding");
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        //HttpServletRequest继承了ServletRequest,这里不强转也是可以的
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        //调用过滤器链执行doFilter()方法,让过滤器链继续执行
        filterChain.doFilter(request,response);
    }

    public void destroy() {

    }
}
