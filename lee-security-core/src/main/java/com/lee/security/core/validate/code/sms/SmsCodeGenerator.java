package com.lee.security.core.validate.code.sms;

import com.lee.security.core.properties.SecurityProperties;
import com.lee.security.core.validate.code.ValidateCode;
import com.lee.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric( securityProperties.getCode().getSmsCodeProperties().getLength() );
        return new ValidateCode( code, securityProperties.getCode().getSmsCodeProperties().getExpireIn() );
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
