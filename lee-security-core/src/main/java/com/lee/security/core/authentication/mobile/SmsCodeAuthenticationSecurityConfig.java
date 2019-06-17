package com.lee.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler leeAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler leeAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure( http );

        //配置过滤器
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        //设置AuthenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager( http.getSharedObject( AuthenticationManager.class ) );

        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler( leeAuthenticationSuccessHandler );
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler( leeAuthenticationFailureHandler );

        //配置provider
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService( userDetailsService );

        //加到安全框架中去
        http.authenticationProvider( smsCodeAuthenticationProvider )
                .addFilterAfter( smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );




    }
}
