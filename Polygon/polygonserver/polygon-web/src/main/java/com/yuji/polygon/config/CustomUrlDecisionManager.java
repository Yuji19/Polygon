package com.yuji.polygon.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @className: CustomUrlDecisionManager
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-13 11:36:00
 */
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection){
            String needPermission = configAttribute.getAttribute();
            if("login".equals(needPermission)){
                //认证用户是否登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("未登录!");
                }else {
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities){
                if (grantedAuthority.equals(needPermission)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足,请联系管理人员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
