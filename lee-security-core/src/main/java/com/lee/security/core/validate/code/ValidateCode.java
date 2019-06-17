package com.lee.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ValidateCode {

    private String code;

    private LocalDateTime expiretime;

    /**
     * 使用的构造函数，构造图片验证码时，传入的参数时多少时间(s)后过期，不是一个过期的指定时间
     * @param code
     * @param expireIn 多少秒时间后过期
     */
    public ValidateCode(String code , int expireIn){
        this.code = code;
        this.expiretime = LocalDateTime.now().plusSeconds( expireIn );
    }

    public ValidateCode(String code, LocalDateTime expiretime){
        this.code = code;
        this.expiretime = expiretime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(LocalDateTime expiretime) {
        this.expiretime = expiretime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter( expiretime );
    }
}
