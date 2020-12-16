package com.yuji.polygon.config;

import com.yuji.polygon.entity.Operation;
import com.yuji.polygon.entity.Role;
import com.yuji.polygon.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: CustomAccessDecisionManager
 * @description: 自定义权限决策管理，
 * @author: yuji
 * @create: 2020-11-13 11:36:00
 */

@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    OperationService operationService;

    @Autowired
    RoleHierarchy roleHierarchy;

    /**
     *
     * @param authentication 包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities
     * @param o FilterInvocation对象，可以得到request等web资源
     * @param collection 本次访问需要的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection){
            String needPermission = configAttribute.getAttribute();
            if("ROLE_LOGIN".equals(needPermission)){
                //认证用户是否登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    //该异常会被Spring Security的AuthenticationEntryPoint捕获，
                    // 即在SecurityConfig中配置authenticationEntryPoint()进行异常处理
                    throw new AccessDeniedException("未登录!");
                }else {
                    return;
                }
            }

            Collection<? extends GrantedAuthority> authorities = roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities());
            String[] roleNames;
            List<String> temp = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            roleNames = temp.toArray(new String[temp.size()]);
            //根据角色查找操作权限
            List<Operation> operations = operationService.getOperationByRoleNames(roleNames);
            for (Operation operation : operations){
                if (operation.getName().equals(needPermission)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足,请联系管理人员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
