package com.yuji.polygon.config;

import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.entity.Operation;
import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.OperationService;
import com.yuji.polygon.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * @className: CustomFilterInvocationSecurityMetadataSource
 * @description: 根据请求URL，分析请求所需权限
 * @author: yuji
 * @create: 2020-11-13 10:38:00
 */

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    OperationService operationService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) obj).getRequestUrl();
        //根据角色获取操作权限
        List<Operation> operations = operationService.getAllOperation();

        for (Operation operation : operations){
            //匹配访问权限
            if (antPathMatcher.match(operation.getUrl(),requestUrl)){
                return SecurityConfig.createList(operation.getName());
            }
        }

        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 返回类对象是否支持校验，web项目一般使用FilterInvocation来判断，或者直接返回true
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
