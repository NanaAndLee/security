package com.lee.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.security.browser.support.SimpleResponse;
import com.lee.security.core.properties.LoginType;
import com.lee.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("leeAuthenticationFailureHandler")
//public class LeeAuthenticationFailureHandler implements AuthenticationFailureHandler {


//可配置后，我们需要两种成功&失败的处理方式，一种是我们写的返回Json
//第二种是跳转方式，这里我们继承AuthenticationFailureHandler的SpringSecurity的默认实现类
//SimpleUrlAuthenticationFailureHandler，它的默认处理是重定向页面正好满足需求
public class LeeAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    private Logger logger = LoggerFactory.getLogger( getClass() );

    @Autowired
    private ObjectMapper objectMapper;//返回信息转换成json格式的工具类

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        logger.info( "登陆失败" );

        if(LoginType.JSON.equals( securityProperties.getBrowser().getLoginType() )){

            //如果配置信息是JSON则返回JSON串
            httpServletResponse.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            httpServletResponse.setContentType( "application/json;charset=UTF-8" );
            httpServletResponse.getWriter().write( objectMapper.writeValueAsString( new SimpleResponse( e.getMessage() ) ) );
        }else{
            //如果配置信息不是JSON，使用父类的默认实现，重定向
            super.onAuthenticationFailure( httpServletRequest, httpServletResponse, e );
        }

    }
}
