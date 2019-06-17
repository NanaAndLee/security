package com.lee.security.core.properties;

import com.lee.security.core.validate.code.ValidateCodeFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lee.security")
public class SecurityProperties {

    //浏览器返回格式相关配置
    private BrowserProperties browser = new BrowserProperties();

    //验证相关配置
    private ValidateCodeProperties code = new ValidateCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}
