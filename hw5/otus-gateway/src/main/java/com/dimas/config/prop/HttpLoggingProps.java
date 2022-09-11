package com.dimas.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "http-logging")
public class HttpLoggingProps {

    private Boolean enabled;
    private List<String> excluded;
}
