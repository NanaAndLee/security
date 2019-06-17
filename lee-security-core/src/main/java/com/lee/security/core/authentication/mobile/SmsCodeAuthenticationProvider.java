package com.lee.security.core.authentication.mobile;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    /**
     * 用UserDetailsService创建一个已认证的Authentication
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;

        UserDetails user = userDetailsService.loadUserByUsername( (String) smsCodeAuthenticationToken.getPrincipal() );

        if (user == null){
            throw new InternalAuthenticationServiceException( "无法获取用户信息" );
        }

        //从新构建已认证的authentication
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken( user, user.getAuthorities() );

        //把之前未认证的details拷贝到已认证的details里
        authenticationResult.setDetails( smsCodeAuthenticationToken.getDetails() );

        return authenticationResult;
    }

    /**
     * 在 {@link org.springframework.security.authentication.AuthenticationManager}中根据supports方法判断是否支持处理当前Token
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom( authentication );
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
