package com.lee.security.core.validate.code;

import com.lee.security.core.properties.SecurityConstants;
import com.lee.security.core.properties.SecurityProperties;
import com.lee.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.lee.security.core.validate.code.ValidateCodeProcessor.SESSION_KEY_PREFIX;

//继承该接口保证该Filter只会被调用一次                                     //配置拦截接口时，实现InitializingBean接口，在其他参数都装配完成后进行Bean - > url的初始化
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

//    private ValidateCodeProcessorHolder validateCodeProcessorHolder = new ValidateCodeProcessorHolder();

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

//    private AuthenticationFailureHandler authenticationFailureHandler;

//    //用来存放配置拦截的url接口
//    private Set<String> urls = new HashSet<>(  );

    //收集需要校验的url
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    @Autowired
    private SecurityProperties securityProperties;

//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    //判断url时 /*不能用equlas进行判断，需要用到这个工具类
    private AntPathMatcher antPathMatcher = new AntPathMatcher(  );



    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put( SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        logger.info(  securityProperties.getCode().getImageCodeProperties().getUrl() );
        logger.info( ValidateCodeType.IMAGE );
        addUrlToMap(securityProperties.getCode().getImageCodeProperties().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSmsCodeProperties().getUrl(), ValidateCodeType.SMS);
    }


    /**
     * 将系统中配置的需要校验的请求根据校验类型放到map中
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type){
        if (StringUtils.isNotBlank( urlString )){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens( urlString, "," );
            for (String url:
                    urls
                 ) {
                urlMap.put( url, type );
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }

        chain.doFilter(request, response);

    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }



//    @Override
//    public void afterPropertiesSet() throws ServletException {
//        super.afterPropertiesSet();
//        //url配置的时候是逗号隔开的这里以","位单位分割成一个个String属组
//        String[] ConfigUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens( securityProperties.getCode().getImageCodeProperties().getUrl(), "," );
//        //循环属组，将配置拦截的url逐个加到集合中
//        for (String configurl:
//                ConfigUrls
//            ) {
//            urls.add( configurl );
//        }
//        //登录时一定要进行图片验证码验证拦截的
//        urls.add( "/authentication/form" );
//    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        //必须是登录请求，必须是POST请求
//
//        boolean action = false;
//        for (String url : urls
//             ) {
//            if (antPathMatcher.match( url, httpServletRequest.getRequestURI() )){
//                action = true;
//            }
//        }
//
////        if (StringUtils.equals( "/authentication/form", httpServletRequest.getRequestURI() )
////            && StringUtils.equalsIgnoreCase( httpServletRequest.getMethod(), "post" )){
//        if(action){
//            //满足要求进行校验
//            try{
//                validate( new ServletWebRequest( httpServletRequest ) );
//            }catch (ValidateCodeException e){
//                //如果校验时抛出异常，失败了，调用登陆失败的处理器
//                authenticationFailureHandler.onAuthenticationFailure( httpServletRequest,
//                        httpServletResponse, e);
//                //出现错误后返回，不再调用后面的过滤器
//                return;
//            }
//        }
//
//        //如果不是登录请求就不做任何请求，调用过滤器链后面的过滤器
//        filterChain.doFilter( httpServletRequest, httpServletResponse );
//    }
//
//    /**
//     * 校验的逻辑
//     * @param request
//     */
//    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
//
//        String SESSION_KEY_SUFFIX = "IMAGE";
//
//
//        //生成的验证码
//        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute( request,
//
//                //uri中没有/code/所以取不到
////                (SESSION_KEY_PREFIX + getProcessorType( request ).toUpperCase()));
//                SESSION_KEY_PREFIX + SESSION_KEY_SUFFIX);
//        System.out.println("============================请求时form的表单提交的请求，所以URI时表单的action");
//        System.out.println("============================走过验证码拦截器的请求："+ request.getRequest().getRequestURI());
//        System.out.println("============================取出session中的验证码键值对" + SESSION_KEY_PREFIX + SESSION_KEY_SUFFIX + codeInSession);
////        System.out.println("============================拦截请求中后面的验证码类型为："+);
//        //用户输入的数，从前端页面中的 name="imageCode" 的text中获得
//        String codeRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
//                "imageCode");
//        System.out.println("============================用户输入的验证码：" + codeRequest);
//
//        if (StringUtils.isBlank( codeRequest )){
//            throw new ValidateCodeException( "验证码不能为空" );
//        }
//
//        if (codeInSession == null){
//            throw new ValidateCodeException( "验证码不存在" );
//        }
//
//        if (codeInSession.isExpried()){
//            //过期，移除验证码
//            sessionStrategy.removeAttribute( request, SESSION_KEY_PREFIX + SESSION_KEY_SUFFIX );
//            throw new ValidateCodeException( "验证码已过期" );
//        }
//
//        if (!StringUtils.equals( codeInSession.getCode(), codeRequest )){
//            throw new ValidateCodeException( "验证码输入错误" );
//        }
//
//        //移除验证码
//        sessionStrategy.removeAttribute( request, SESSION_KEY_PREFIX + SESSION_KEY_SUFFIX );
//    }
//
//    //无效
////    private String getProcessorType(ServletWebRequest request) {
////        return StringUtils.substringAfter( request.getRequest().getRequestURI(), "/code/" );
////    }







    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

//    public SessionStrategy getSessionStrategy() {
//        return sessionStrategy;
//    }
//
//    public void setSessionStrategy(SessionStrategy sessionStrategy) {
//        this.sessionStrategy = sessionStrategy;
//    }

//    public Set<String> getUrls() {
//        return urls;
//    }
//
//    public void setUrls(Set<String> urls) {
//        this.urls = urls;
//    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public ValidateCodeProcessorHolder getValidateCodeProcessorHolder() {
        return validateCodeProcessorHolder;
    }

    public void setValidateCodeProcessorHolder(ValidateCodeProcessorHolder validateCodeProcessorHolder) {
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
    }

    public Map<String, ValidateCodeType> getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(Map<String, ValidateCodeType> urlMap) {
        this.urlMap = urlMap;
    }

    public AntPathMatcher getAntPathMatcher() {
        return antPathMatcher;
    }

    public void setAntPathMatcher(AntPathMatcher antPathMatcher) {
        this.antPathMatcher = antPathMatcher;
    }
}
