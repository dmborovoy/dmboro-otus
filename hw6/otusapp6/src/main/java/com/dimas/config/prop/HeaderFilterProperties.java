package com.dimas.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

import static com.dimas.util.Constant.ROOT_PATH;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "header-filter")
public class HeaderFilterProperties {
    private List<String> urlList = List.of(ROOT_PATH + "/*");
    private List<String> excludedUrls = List.of();
    private List<String> optionalGatewayHeaderNames = List.of();
}
