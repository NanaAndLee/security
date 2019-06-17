package com.lee.security.browser;

import com.lee.security.browser.authentication.LeeAuthenticationFailureHandler;
import com.lee.security.browser.authentication.LeeAuthenticationSuccessHandler;
import com.lee.security.core.authentication.AbstractChannelSecurityConfig;
import com.lee.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.lee.security.core.properties.SecurityConstants;
import com.lee.security.core.properties.SecurityProperties;
import com.lee.security.core.validate.code.SmsCodeFilter;
import com.lee.security.core.validate.code.ValidateCodeFilter;
import com.lee.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

//继承配置类   适配器类
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    //认证格式
    //授权
    //任何请求
    //都需要身份认证
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;//给系统或者用户配置的登录路径配置权限

    @Autowired
    private LeeAuthenticationSuccessHandler leeAuthenticationSuccessHandler;//注入自定义的登录成功的拦截器

    @Autowired
    private LeeAuthenticationFailureHandler leeAuthenticationFailureHandler;//注入自定义的登录失败拦截器

    //记住我的数据源，DataSource的配置在demo的yml配置
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }


    //记住我配置
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource( dataSource );
        //启动时会自动创建表，也可以到到JdbcTokenRepositoryImpl类里拿建表语句自己去建表
//        tokenRepository.setCreateTableOnStartup( true );
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http
                .apply( validateCodeSecurityConfig )
                    .and()
                .apply( smsCodeAuthenticationSecurityConfig )
                    .and()
                .rememberMe()
                    .tokenRepository( persistentTokenRepository() )
                    .tokenValiditySeconds( securityProperties.getBrowser().getRememberMeSeconds() )
                    .userDetailsService( userDetailsService )
                    .and()
                .authorizeRequests()
                    .antMatchers(
                            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                            securityProperties.getBrowser().getLoginPage(),
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*"
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .csrf().disable();






    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
////        图形验证码配置
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        //传入自定义的失败处理器
//        validateCodeFilter.setAuthenticationFailureHandler( leeAuthenticationFailureHandler );
//
//        //把配置传进入
//        validateCodeFilter.setSecurityProperties( securityProperties );
//        //然后调初始化方法
//        validateCodeFilter.afterPropertiesSet();
//
////        短信验证码配置
//        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
//        //传入自定义的失败处理器
//        smsCodeFilter.setAuthenticationFailureHandler( leeAuthenticationFailureHandler );
//
//        //把配置传进入
//        smsCodeFilter.setSecurityProperties( securityProperties );
//        //然后调初始化方法
//        smsCodeFilter.afterPropertiesSet();
//
//        //表单登录验证
//        http
//                //添加短信验证码过滤器到过滤器链上
//                .addFilterBefore( smsCodeFilter, UsernamePasswordAuthenticationFilter.class )
//                //添加图片验证码校验的过滤器
//                .addFilterBefore( validateCodeFilter, UsernamePasswordAuthenticationFilter.class )
//
//                //登表单登陆配置
//                .formLogin()
//                //        http.httpBasic()
//                    .loginPage( "/authentication/require" )
//                    .loginProcessingUrl( "/authentication/form" )
//                    .successHandler( leeAuthenticationSuccessHandler )
//                    .failureHandler( leeAuthenticationFailureHandler )
//                    .and()
//
//                //记住我配置
//                .rememberMe()
//                    .tokenRepository( persistentTokenRepository() )
//                    .tokenValiditySeconds( securityProperties.getBrowser().getRememberMeSeconds() )
//                    .userDetailsService( userDetailsService )
//                    .and()
//
//                .authorizeRequests()             // 授权
//                .antMatchers( "/authentication/require",
//                         securityProperties.getBrowser().getLoginPage(),
//                        "/code/*").permitAll()
//                .anyRequest()
//                .authenticated()// 身份认证
//                .and()
//                .csrf().disable()
//                .apply( smsCodeAuthenticationSecurityConfig );
//    }
}
