package com.lee.security.core.validate.code.impl;

import com.lee.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor <C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

//    依赖搜索！！！！！！！！！！
    /**
     * 收集系统中所有 {@link ValidateCodeGenerator} 接口的实现
     * 它会找到所有ValidateCodeGenerator的实现，以bean的名字作为key,该类作为值，放到Map里面，
     * 用的时候再根据请求从Map中找到相应的验证码生成器实现类
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        //1.生成验证码
        C validateCode = generate(request);
        //2.保存校验码
        save(request, validateCode);
        //发送
        send(request, validateCode);
    }

    /**
     * 生成校验码
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
//        String type = getValidateCodeType(request).toString().toLowerCase();
//        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
//        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get( generatorName );
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get( type + "CodeGenerator");
        if (validateCodeGenerator == null){
            throw new ValidateCodeException( "验证码生成器" + type + "CodeGenerator" +"不存在" );
        }
        //找到对应的实现类，调取验证码生成方法
       return (C) validateCodeGenerator.generate( request );
    }

    /**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter( request.getRequest().getRequestURI(), "/code/" );
    }

    /**
     * 保存校验码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
//        sessionStrategy.setAttribute( request, (SESSION_KEY_PREFIX + getProcessorType( request )).toUpperCase(),validateCode );
        sessionStrategy.setAttribute( request, getSessionKey( request ), validateCode );
        System.out.println("验证码类型：" + getProcessorType( request ));
        System.out.println("=======================生成的SESSION_KEY_PREFIX + 后缀 放入session中： " + SESSION_KEY_PREFIX + getProcessorType( request ).toUpperCase() +"值为 :  "+ validateCode);
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 发送验证码，由子类实现
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


    /**
     * 校验逻辑，手机短信验证码登录和图片验证码登录相同
     *
     * @param request
     */
    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
