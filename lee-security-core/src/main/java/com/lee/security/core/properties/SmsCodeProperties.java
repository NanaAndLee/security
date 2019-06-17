package com.lee.security.core.properties;

//短信验证码的配置类
public class SmsCodeProperties {
//短信验证码的基本参数可配置
    private int length = 6;//验证码的长度（个数），手机验证码一般是6位
    private int expireIn = 60;//验证码过期时间（默认60s）

//验证码拦截的接口可配置
    private String url;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
