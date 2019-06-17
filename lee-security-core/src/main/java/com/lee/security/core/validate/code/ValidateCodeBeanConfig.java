package com.lee.security.core.validate.code;

import com.lee.security.core.properties.SecurityProperties;
import com.lee.security.core.validate.code.image.ImageCodeGenerator;
import com.lee.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.lee.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration和在imageCodeGenerator上加上注解@Component效果是相同的
//不同的时这里可以在Bean上指定另一个注解@ConditionalOnMissingBean(name = "imageCodeGenerator")
@Configuration
public class ValidateCodeBeanConfig {

    //这里初始化的时候需要系统的配置信息,将配置类注入进来
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    //spring找不到imageCodeGenerator Bean的时候才会调用该初始化方法
    //先在Spring容器中去找这个bean如果存在就不会初始化，不存在时才会使用这个配置
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties( securityProperties );
        return codeGenerator;
    }

    @Bean
    //spring找不到imageCodeGenerator Bean的时候才会调用该初始化方法
    //先在Spring容器中去找这个bean如果存在就不会初始化，不存在时才会使用这个配置
//    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    /*
        当系统找到了其他的类实现了SmsCodeSender这个接口的方法时，系统就不会初始化这个Bean,
        会使用其他的实现，可以覆盖默认实现
     */
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }





}
