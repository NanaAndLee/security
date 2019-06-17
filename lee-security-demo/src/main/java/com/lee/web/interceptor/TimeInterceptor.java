package com.lee.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component //与filter不同的是，使用该注解之后并不能起作用
public class TimeInterceptor implements HandlerInterceptor {

    /**
     * 控制器方法调用之前调用
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("preHandle");

        System.out.println( ((HandlerMethod)o).getBean().getClass().getName() );
        System.out.println(((HandlerMethod)o).getMethod().getName());

        httpServletRequest.setAttribute( "startTime", new Date(  ).getTime() );
        return true;//控制是否调用下面的两个方法
    }

    /**
     * 访问的控制器方法调用之后调用，如果控制器方法抛出了一场，则该方法不会被调用
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        System.out.println("postHandler");
        Long start = (Long)httpServletRequest.getAttribute( "startTime" );
        System.out.println("time interceptor 用时 : " + (new Date(  ).getTime() - start) + "ms");
    }

    /**
     * 无论是否抛出异常，在Controller方法调用之后都会调用该方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        System.out.println("afterHandler");
        Long start = (Long)httpServletRequest.getAttribute( "startTime" );
        System.out.println("time interceptor 用时 : " + (new Date(  ).getTime() - start) + "ms");
        System.out.println("e is : " + e);
    }
}
