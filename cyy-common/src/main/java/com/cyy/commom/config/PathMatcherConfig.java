package com.cyy.commom.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

//配置文件中需要有 sys.enable-my-security=true  这个bean才能起效
@ConditionalOnProperty(prefix = "sys",name = "enable-my-security",havingValue = "true")
@Configuration
public class PathMatcherConfig {

    //路径白名单创建
    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}
