package com.yuji.polygon.config;

import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @className: CustomFilterInvocationSecurityMetadataSource
 * @description: 根据请求URL，分析请求所需权限
 * @author: yuji
 * @create: 2020-11-13 10:38:00
 */

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    PermissionService permissionService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) obj).getRequestUrl();
        //根据角色获取操作权限和菜单权限
        List<Permission> permissions = permissionService.getAllPermissionByRole();
        String[] own = new String[permissions.size()];
        for (int i = 0; i < permissions.size(); i++){
            Permission permission = permissions.get(i);
            List<Menu> menus = permission.getMenus();
            for (Menu menu : menus){
                //匹配菜单权限
                if (antPathMatcher.match(menu.getUrl()+"/**",requestUrl)){
                    //匹配操作权限
                    if (requestUrl.contains(permission.getName())){
                        //菜单权限+操作权限 exp: employee_add
                        own[i]=menu.getUrl().substring(1)+"_"+permission.getName();
                    }
                    break;
                }
            }
        }

        //判断own数组中的实际元素个数，大于0，有请求所需的权限
        if (Arrays.stream(own).count() > 0){
            return SecurityConfig.createList(own);
        }

        return SecurityConfig.createList("login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
