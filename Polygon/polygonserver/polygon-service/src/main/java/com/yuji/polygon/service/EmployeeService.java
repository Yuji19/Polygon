package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: EmployeeService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-12 10:01:00
 */
public class EmployeeService implements UserDetailsService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String employeeNo) throws UsernameNotFoundException {
        Employee employee = employeeMapper.getEmployeeByEmployeeNo(employeeNo);
        if (employee == null){
            throw new UsernameNotFoundException(String.format("No user found with username: %s", employeeNo));
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = roleService.getRoleByEmployeeId(employee.getId());
        employee.setRoles(roles);
        for (Role role : roles){
            //根据角色查找菜单和权限
            List<Menu> menus = menuService.getMenuWithPermissionByRoleId(role.getId());
            for (Menu menu : menus){
                List<Permission> permissions = menu.getPermissions();
                for (Permission permission : permissions){
                    authorities.add(new SimpleGrantedAuthority(permission.getName()));
                }

            }
        }
        //去重 由于菜单基本都具有增删改查,所以只保留一个即可
        authorities = authorities.stream().distinct().collect(Collectors.toList());
        //Employee没有实现UserDetails接口，需要返回spring sercurity的User
        return new User(employee.getEmployeeNo(),employee.getPassword(),employee.isEnabled(),true,true,true,authorities);
    }
}
