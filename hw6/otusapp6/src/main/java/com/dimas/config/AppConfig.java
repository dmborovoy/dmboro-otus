package com.dimas.config;

import com.dimas.config.prop.HeaderFilterProperties;
import com.dimas.exception.ExceptionResponseHandler;
import com.dimas.filter.HeaderFilter;
import com.dimas.filter.HeaderStore;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableConfigurationProperties(value = {HeaderFilterProperties.class})
public class AppConfig {

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

    @Bean
    public HeaderFilter userDetailsFilter(
            AntPathMatcher antPathMatcher,
            HeaderStore headerStore,
            ExceptionResponseHandler exceptionResponseHandler,
            HeaderFilterProperties config) {
        return new HeaderFilter(antPathMatcher, headerStore, exceptionResponseHandler, config);
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> userDetailsFilterRegistration(HeaderFilter userDetailsFilter, HeaderFilterProperties config) {
        FilterRegistrationBean<HeaderFilter> result = new FilterRegistrationBean<>();
        result.setFilter(userDetailsFilter);
        result.setUrlPatterns(config.getUrlList());
        result.setName("Header Store Filter");
        result.setOrder(1);
        return result;
    }

    @Bean(destroyMethod = "destroy")
    public ThreadLocalTargetSource threadLocalUserDetailsStore() {
        ThreadLocalTargetSource result = new ThreadLocalTargetSource();
        result.setTargetBeanName("headerStore");
        return result;
    }

    @Primary
    @Bean(name = "proxiedThreadLocalTargetSource")
    public ProxyFactoryBean proxiedThreadLocalTargetSource(ThreadLocalTargetSource threadLocalTargetSource) {
        ProxyFactoryBean result = new ProxyFactoryBean();
        result.setTargetSource(threadLocalTargetSource);
        return result;
    }

    @Bean(name = "headerStore")
    @Scope(scopeName = "prototype")
    public HeaderStore headerStore() {
        return new HeaderStore();
    }
}
