package com.dimas.config;

import com.dimas.config.prop.HttpLoggingProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableCaching
@EnableConfigurationProperties(value = {HttpLoggingProps.class})
public class ApplicationConfig {

    @Bean
    protected AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

}
