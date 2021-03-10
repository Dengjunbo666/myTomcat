package com.dengjunbo.javaweb;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class URLFilter implements Filter{
 
    @Override
    public void destroy() {
        System.out.println("URLFilter 的 destroy() 被调用");
    }
     
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
         
        HttpServletRequest request =  (HttpServletRequest) servletRequest;
        String url = request.getRequestURL().toString();
        System.out.println("url filter:" + url);
        filterChain.doFilter(servletRequest, servletResponse);
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         
    }
 
}