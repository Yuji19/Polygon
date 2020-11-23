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

import java.util.ArrayList;
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
        List<String> own = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++){
            Permission permission = permissions.get(i);
            List<Menu> menus = permission.getMenus();
            for (Menu menu : menus){
                //匹配菜单权限
                if (antPathMatcher.match(menu.getMeta().getUrl()+"/**",requestUrl)){
                    //匹配操作权限
                    if (requestUrl.contains(permission.getName())){
                        //菜单权限+操作权限 exp: employee_add
                        own.add(menu.getMeta().getUrl().substring(1)+"_"+permission.getName());
                    }
                    break;
                }
            }
        }

        //判断own是否有元素，确定请求需不需要权限
        if (own != null && own.size() > 0){
            return SecurityConfig.createList(own.toArray(new String[own.size()]));
        }

        return SecurityConfig.createList("login");
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
