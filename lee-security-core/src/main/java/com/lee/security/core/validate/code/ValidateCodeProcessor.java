package com.lee.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验处理逻辑
 *
 * @author litenghui
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放到session的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE";

    /**
     *
     * @param request (ServletWebRequest是Spring的一个工具类，request和response都可以放里面)
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);

}
