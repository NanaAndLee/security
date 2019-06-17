package com.lee.security.core.properties;

//封装各个验证码配置的类
public class ValidateCodeProperties {

    private ImageCodeProperties imageCodeProperties;

    private SmsCodeProperties smsCodeProperties;

    public ImageCodeProperties getImageCodeProperties() {
        return imageCodeProperties;
    }

    public void setImageCodeProperties(ImageCodeProperties imageCodeProperties) {
        this.imageCodeProperties = imageCodeProperties;
    }

    public SmsCodeProperties getSmsCodeProperties() {
        return smsCodeProperties;
    }

    public void setSmsCodeProperties(SmsCodeProperties smsCodeProperties) {
        this.smsCodeProperties = smsCodeProperties;
    }
}
