package com.lee.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

@Component //在filter中使用该注解就对所有的url都起作用了    第二种使用方式是在WebConfig中去配置
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("======TimeFilter Init======");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("======TimeFilter start======");
        Long start = new Date(  ).getTime();
        filterChain.doFilter( servletRequest, servletResponse );
        System.out.println("TimFilter 用时 : " + (new Date(  ).getTime() - start)+"ms");
        System.out.println("======TimeFilter finsh======");
    }

    @Override
    public void destroy() {
        System.out.println("======TimeFilter Destroy======");
    }
}
