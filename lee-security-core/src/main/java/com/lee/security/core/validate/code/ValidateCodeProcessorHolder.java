package com.lee.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateCodeProcessorHolder {
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        System.out.println(type.toString());
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {


        /**
         * 注意  我们这里组件的名称不一样 {@link com.lee.security.core.validate.code.image.ImageCodeProcessor}
         * 和 {@link com.lee.security.core.validate.code.sms.SmsCodeProcessor} 他们的bean名称是没有Validate的
         *
         * 或者把两个bean的名称改一下
         */
//        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        String name = type.toLowerCase() + "CodeProcessor";
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }
}
