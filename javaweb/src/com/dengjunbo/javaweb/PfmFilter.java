package com.dengjunbo.javaweb;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class PfmFilter implements Filter{
 
    @Override
    public void destroy() {
         
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
         
        HttpServletRequest request =  (HttpServletRequest) servletRequest;
         
        String url = request.getRequestURL().toString();
         
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long end = System.currentTimeMillis();
        System.out.println((end-start) + " ms elapsed on url:" +url);
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("PrmFilter 的初始化参数：");
        Enumeration<String> e = filterConfig.getInitParameterNames();
        while(e.hasMoreElements()){
            String name = e.nextElement();
            String value = filterConfig.getInitParameter(name);
            System.out.println("name:" + name);
            System.out.println("value:" + value);
        }
    }
 
}