package com.yuji.polygon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * @className: PolygonWebApplication
 * @description: 启动类
 * @author: yuji
 * @create: 2020-11-09 10:59
 **/

@SpringBootApplication
@EnableCaching
public class PolygonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolygonWebApplication.class, args);
    }

}
