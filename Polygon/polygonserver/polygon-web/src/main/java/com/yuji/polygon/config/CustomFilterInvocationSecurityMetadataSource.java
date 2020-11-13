package com.yuji.polygon.config;

import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @className: CustomFilterInvocationSecurityMetadataSource
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-13 10:38:00
 */

public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) obj).getRequestUrl();
        //根据角色获取菜单以及每个菜单对应的权限
        List<Menu> menus = menuService.getAllMenuWithPermission();
        for (Menu menu : menus){
            //根据菜单，获取在该菜单的操作权限
            List<Permission> permissions = menu.getPermissions();
            String[] own = new String[permissions.size()];
            for (int i = 0; i < permissions.size(); i++){
                Permission permission = permissions.get(i);
                //操作对应的拦截URL匹配请求URL ; 0个或多个目录.** 代表的字符串本身不一定要包含 /
                if (antPathMatcher.match(menu.getUrl()+"/"+permission.getName()+"**",requestUrl)){
                    own[i]=permission.getName();
                    return SecurityConfig.createList(own);
                }
            }
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
