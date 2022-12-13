package com.dimas.profile.config;

import com.dimas.common.ExceptionResponseHandler;
import com.dimas.common.ObjectMapperFactory;
import com.dimas.common.UserDetailsStore;
import com.dimas.filter.UserDetailsFilter;
import com.dimas.filter.UserDetailsFilterProperties;
import com.dimas.profile.config.prop.KeycloakProps;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.util.AntPathMatcher;

@Slf4j
@EnableConfigurationProperties(value = {UserDetailsFilterProperties.class, KeycloakProps.class})
public class AppConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();

        xStream.allowTypesByWildcard(new String[]{
                "com.**"
        });
        return xStream;
    }

    @Bean
    ObjectMapper objectMapper(){
        return ObjectMapperFactory.getObjectMapper();
    }

    @Bean
    ExceptionResponseHandler exceptionResponseHandler(ObjectMapper objectMapper){
        return new ExceptionResponseHandler(objectMapper);
    }

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
