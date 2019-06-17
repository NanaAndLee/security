package com.lee.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.security.core.properties.LoginType;
import com.lee.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("leeAuthenticationSuccessHandler")
//public class LeeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//可配置登陆处理，这里不再实现接口，而是继承SpringSecurity的默认登录成功处理器该处理器处理方式时跳转，，覆盖里面的方法
public class LeeAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication SpringSecurity核心接口，封装认证信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info( "登陆成功" );

        //判断配置的登录成功失败的处理格式，如果应用程序的配置文件里配置的时JSON，就是用虾米那的格式
        if (LoginType.JSON.equals( securityProperties.getBrowser().getLoginType() )){

            //登陆成功之后将通过认证的authentication以json的形式返回前台
            httpServletResponse.setContentType( "application/json;charset=UTF-8" );
            httpServletResponse.getWriter().write( objectMapper.writeValueAsString( authentication ) );
        }else {
            //如果是跳转，就是用父类里的方法，SavedRequestAwareAuthenticationSuccessHandler里的默认实现是跳转
            super.onAuthenticationSuccess( httpServletRequest, httpServletResponse, authentication );
        }

    }
}
