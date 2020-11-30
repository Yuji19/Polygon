package com.yuji.polygon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: LoginFilter
 * @description: 自定义登录身份验证过滤器
 * @author: yuji
 * @create: 2020-11-18 15:37:00
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //UsernamePasswordAuthenticationFilter默认过滤/login路径 并且强制只对post请求应用
        if (!"POST".equals(request.getMethod())){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //获取服务端生成的验证码
        String verifyCode = (String)request.getSession().getAttribute("verifyCode");
        //判断请求内容的类型 获取请求内容
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
            Map<String,Object> requestData = new HashMap<>(4);
            ObjectMapper om = new ObjectMapper();
            try {
                //从请求的输入流中获取数据转为Map类型
                requestData = om.readValue(request.getInputStream(),Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                //校验验证码
                String code = (String) requestData.get("code");
                checkVerifyCode(verifyCode,code);
            }

            //usernameParamenter默认值是username  要在SecurityConfig中设置为employeeNo
            String employeeNo = (String)requestData.get(getUsernameParameter());
            String password = (String)requestData.get(getPasswordParameter());
            if (employeeNo == null){
                employeeNo = "";
            }
            if (password == null){
                password = "";
            }
            employeeNo = employeeNo.trim();

            /**
             * UsernamePasswordAuthenticationToke实现了Authenication接口，封装用户名和密码
             * 继承AbstractAuthenticationToken是为了针对使用用户名和密码验证的请求按照约定进行了一定的封装：
             * 将employeeNo赋值到了principal ，而将password赋值到了credentials
             */
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(employeeNo,password);
            //将来自HTTP请求中的参数按照预先约定放入赋值给Authentication指定属性
            setDetails(request,authRequest);
            //账号密码验证通过后，注册session  即前端的JESSIONID
            sessionRegistry.registerNewSession(request.getSession().getId(),authRequest.getPrincipal());
            return this.getAuthenticationManager().authenticate(authRequest);
        }else {
            checkVerifyCode(verifyCode,request.getParameter("code"));
            return super.attemptAuthentication(request, response);
        }
    }

    /**
     *
     * @param verifyCode 服务端生成的验证码
     * @param code 前端填写的验证码
     */
    public void checkVerifyCode(String verifyCode, String code){
        if (code == null || "".equals(code) || verifyCode == null || !verifyCode.toLowerCase().equals(code.toLowerCase())){
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}
