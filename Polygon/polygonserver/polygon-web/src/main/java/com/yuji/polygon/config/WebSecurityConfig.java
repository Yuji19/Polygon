package com.yuji.polygon.config;

import com.yuji.polygon.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @className: WebSecurityConfig
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-12 10:46:00
 */

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    EmployeeService employeeService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(employeeService);
    }

    /**
     * 后端接口访问权限控制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }
}
