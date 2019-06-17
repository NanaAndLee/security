package com.lee.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

//校验码生成器接口，提供拓展生成更高级的图片验证码生成逻辑
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
