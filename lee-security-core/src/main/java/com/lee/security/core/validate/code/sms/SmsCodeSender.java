package com.lee.security.core.validate.code.sms;

public interface SmsCodeSender {

    /**
     * 调用短信服务向手机发送短信验证码
     * @param mobile 手机号
     * @param code 验证码
     */
    void send(String mobile, String code);
}
