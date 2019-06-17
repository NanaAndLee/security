package com.lee.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 封装认证信息
 * 把UsernamePasswordAuthenticationToken拷贝过来，去掉与密码相关的东西
 * @author litenghui
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 420L;
    private final Object principal;//放认证信息 1.认证之前放手机号 2.认证之后放登陆成功的用户
//    private Object credentials;//在AuthenticationUsernamePassword里用来存放密码的，手机登录没有密码

    /**
     * 未登录时存放手机号
     * @param mobile
     */
    public SmsCodeAuthenticationToken(String mobile) {
        super((Collection)null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
