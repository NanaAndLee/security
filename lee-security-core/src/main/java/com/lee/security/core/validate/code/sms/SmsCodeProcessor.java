package com.lee.security.core.validate.code.sms;

import com.lee.security.core.validate.code.ValidateCode;
import com.lee.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 *
 * @author litenghui
 */
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        //从请求中获取手机号
        String mobile = ServletRequestUtils.getRequiredStringParameter( request.getRequest(),  "mobile");
        System.out.println("==============================用户输入手机号：" + mobile);
        smsCodeSender.send( mobile, validateCode.getCode() );
        System.out.println("==============================向手机发送验证码："+validateCode.getCode());
    }
}
