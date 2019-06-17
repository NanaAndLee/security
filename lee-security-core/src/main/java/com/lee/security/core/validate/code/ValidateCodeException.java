package com.lee.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

//继承权限认证过程中所有异常类的基类 AuthenticationException
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -7061385249121687926L;

    public ValidateCodeException(String msg) {
        super( msg );
    }
}
