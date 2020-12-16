package com.yuji.polygon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import com.yuji.polygon.entity.Role;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;


import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.List;

/**
 * @className: WebSecurityConfig
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-12 10:46:00
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleService roleService;

    /**
     * 自定义的过滤请求访问权限源
     */
    @Autowired
    CustomFilterInvocationSecurityMetadataSource securityMetadataSource;

    /**
     * 自定义的访问决策器
     */
    @Autowired
    CustomAccessDecisionManager accessDecisionManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeService);
    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    SessionRegistryImpl sessionRegistry(){
        return new SessionRegistryImpl();
    }

    /**
     * 注册Servlet Listener,用于发布Session的创建和销毁事件
     */
    @Bean
    public ServletListenerRegistrationBean httpSessionEventPublisher() {
        ServletListenerRegistrationBean<HttpSessionEventPublisher> registration = new ServletListenerRegistrationBean<>();
        registration.setListener(new HttpSessionEventPublisher());
        return registration;
    }

    /**
     * 角色继承
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        List<Role> roles = roleService.getAllRole();
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "";
        for (Role role : roles){
            if (!"ROLE_employee".equals(role.getName()) && !"".equals(role.getName())){
                hierarchy+=role.getName()+" > ROLE_employee \n ";
            }

        }
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();

        //自定义usernameParameter
        loginFilter.setUsernameParameter("no");

        //设置身份验证成功处理
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            ResultVO<String> result = new ResultVO("登录成功");
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
            out.close();
        });

        //设置身份验证失败处理
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            String msg = exception.getLocalizedMessage();
            if (exception instanceof LockedException) {
                msg = "账户被锁定，请联系管理员!";
            } else if (exception instanceof CredentialsExpiredException) {
                msg = "密码过期，请联系管理员!";
            } else if (exception instanceof AccountExpiredException) {
                msg = "账户过期，请联系管理员!";
            } else if (exception instanceof DisabledException) {
                msg = "账户被禁用，请联系管理员!";
            } else if (exception instanceof BadCredentialsException) {
                msg = "用户名或者密码输入错误，请重新输入!";
            }
            ResultVO<String> result = new ResultVO(ResultCode.FAILED,msg);
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
            out.close();
        });

        loginFilter.setAuthenticationManager(authenticationManagerBean());
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        //同一用户只能有一个会话
        sessionStrategy.setMaximumSessions(1);
        //设置session控制策略
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }

    /**
     * 后端接口访问权限控制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(accessDecisionManager);
                        object.setSecurityMetadataSource(securityMetadataSource);
                        return object;
                    }
                })
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    ResultVO<String> result = new ResultVO("注销成功");
                    out.write(new ObjectMapper().writeValueAsString(result));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    //http 401 请求要求身份验证。对于需要登录的网页，服务器可能返回此响应
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    String msg = "访问失败，无认证";
                    if (exception instanceof InsufficientAuthenticationException){
                        msg = "请求要求身份验证";
                    }
                    ResultVO<String> result = new ResultVO(ResultCode.FAILED,msg);
                    out.write(new ObjectMapper().writeValueAsString(result));
                    out.flush();
                    out.close();
                });

                //为ConcurrentSessionFilter设置session控制策略
                http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(),event->{
                    HttpServletResponse response = event.getResponse();
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    ResultVO<String> result = new ResultVO(ResultCode.FAILED,"该账户在其他设备上登录了");
                    out.write(new ObjectMapper().writeValueAsString(result));
                    out.flush();
                    out.close();
                }),ConcurrentSessionFilter.class);
                //http.addFilterAt() 不能替换默认的过滤器，只是在相同的位置放置一个过滤器，原本的过滤器仍然起作用
                //添加自定义的loginFilter
                http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) {
        //静态资源的访问不需要验证
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
    }
}
