package com.dimas.config;

import com.dimas.config.prop.KeycloakProps;
import com.dimas.config.prop.UserDetailsFilterProperties;
import com.dimas.exception.ExceptionResponseHandler;
import com.dimas.filter.UserDetailsFilter;
import com.dimas.security.UserDetailsStore;
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
@EnableConfigurationProperties(value = {UserDetailsFilterProperties.class, KeycloakProps.class})
public class AppConfig {

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

    @Bean
    public UserDetailsFilter userDetailsFilter(
            AntPathMatcher antPathMatcher,
            UserDetailsStore userDetailsStore,
            ExceptionResponseHandler exceptionResponseHandler,
            UserDetailsFilterProperties config) {
        return new UserDetailsFilter(antPathMatcher, userDetailsStore, exceptionResponseHandler, config);
    }

    @Bean
    public FilterRegistrationBean<UserDetailsFilter> userDetailsFilterRegistration(UserDetailsFilter userDetailsFilter, UserDetailsFilterProperties config) {
        FilterRegistrationBean<UserDetailsFilter> result = new FilterRegistrationBean<>();
        result.setFilter(userDetailsFilter);
        result.setUrlPatterns(config.getUrlList());
        result.setName("User Details Store Filter");
        result.setOrder(1);
        return result;
    }

    @Bean(destroyMethod = "destroy")
    public ThreadLocalTargetSource threadLocalUserDetailsStore() {
        ThreadLocalTargetSource result = new ThreadLocalTargetSource();
        result.setTargetBeanName("userDetailsStore");
        return result;
    }

    @Primary
    @Bean(name = "proxiedThreadLocalTargetSource")
    public ProxyFactoryBean proxiedThreadLocalTargetSource(ThreadLocalTargetSource threadLocalTargetSource) {
        ProxyFactoryBean result = new ProxyFactoryBean();
        result.setTargetSource(threadLocalTargetSource);
        return result;
    }

    @Bean(name = "userDetailsStore")
    @Scope(scopeName = "prototype")
    public UserDetailsStore userDetailsStore() {
        return new UserDetailsStore();
    }
}
