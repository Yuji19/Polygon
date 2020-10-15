package com.yuji.polygon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @className: CORSConfiguration
 * @description: 跨域配置
 * @author: yuji
 * @create: 2020-10-15 20:47
 **/

@Configuration
public class CORSConfiguration extends WebMvcConfigurationSupport {
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .exposedHeaders("*")
                .maxAge(20000);
    }
}
