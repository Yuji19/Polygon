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
                //允许任何域名
                .allowedOrigins("*")
                //允许任何头
                .allowedHeaders("*")
                //允许任何方法
                .allowedMethods("*")
                //允许暴露的响应头
                .exposedHeaders("access-control-allow-headers","access-control-allow-methods",
                        "access-control-allow-origin","access-control-max-age",
                        "X-Frame-Options","Authorization")
                .maxAge(3600);
    }
}
