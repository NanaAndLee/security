package com.lee.web.config;

import com.lee.web.filter.TimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用这种方式调用可以将第三方的非spring的过滤器加入到工程中
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(  );

        TimeFilter timeFilte = new TimeFilter();
        registrationBean.setFilter( timeFilte );

        //设置过滤的url
        List<String> urls = new ArrayList<>(  );
        urls.add( "/*" );
        //将url集合添加至拦截器内
        registrationBean.setUrlPatterns( urls );

        return registrationBean;
    }
}
