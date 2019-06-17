package com.lee.security.core.authentication;

import com.lee.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 账号密码登陆相关的配置
 *
 * @author litenhgui
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected AuthenticationSuccessHandler leeAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler leeAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage( SecurityConstants.DEFAULT_UNAUTHENTICATION_URL )
                .loginProcessingUrl( SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM )
                .successHandler( leeAuthenticationSuccessHandler )
                .failureHandler( leeAuthenticationFailureHandler );

    }






}
