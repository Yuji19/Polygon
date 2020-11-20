package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.EmployeeMapper;
import com.yuji.polygon.mapper.EmployeeRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @className: EmployeeService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-12 10:01:00
 */

@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    EmployeeRoleMapper employeeRoleMapper;

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
            //根据角色查找权限
            List<Permission> permissions = permissionService.getPermissionByRoleId(role.getId());
            for (Permission permission : permissions){
                List<Menu> menus = permission.getMenus();
                for (Menu menu : menus){
                    //菜单权限+操作权限 exp: employee_add
                    String own = menu.getUrl().substring(1)+"_"+permission.getName();
                    authorities.add(new SimpleGrantedAuthority(own));
                }

            }

        }
        //Employee没有实现UserDetails接口，需要返回spring sercurity的User
        return new User(employee.getEmployeeNo(),employee.getPassword(),employee.isEnabled(),true,true,true,authorities);
    }

    /**
     * 增加员工以及该员工所拥有的角色
     * @param employee
     * @param rids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertEmployee(Employee employee,int[] rids){
        int result1 = employeeMapper.insertEmployee(employee);
        int result2 = employeeRoleMapper.insertEmployeeRole(employee.getId(),rids);

        return (result1 > 0 && result2 > 0) ? 1:0;
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    public int deleteEmployeeById(int id){
        return employeeMapper.deleteEmployeeById(id);
    }

    /**
     * 增加员工的角色
     * @param eid
     * @param rids
     * @return
     */
    public int insertEmpolyeeRole(int eid, int[] rids){
        return employeeRoleMapper.insertEmployeeRole(eid,rids);
    }

    /**
     * 删除员工拥有的某些角色
     * @param eid
     * @param rids
     * @return
     */
    public int deleteEmpolyeeRoleByEidAndRid(int eid, int[] rids){
        return employeeRoleMapper.deleteEmployeeRoleByEidAndRid(eid,rids);
    }

    /**
     * 更新员工基本信息
     * @param employee
     * @return
     */
    public int updateEmployee(Employee employee){
        employee.setGmtModified(new Date());
        return employeeMapper.updateEmployee(employee);
    }

    /**
     * 根据员工编号获取员工
     * @param employeeNo
     * @return
     */
    public Employee getEmployeeByEmployeeNo(String employeeNo){
        return employeeMapper.getEmployeeByEmployeeNo(employeeNo);
    }

    /**
     * 分页查询
     * @param employee
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<Employee> getAllEmployee(Employee employee, int pageNum, int pageSize){
        int startIndex = (pageNum-1)*pageSize;
        int totalCount = employeeMapper.countTotalEmployee(employee);
        List<Employee> records = employeeMapper.getAllEmployee(employee,startIndex,pageSize);
        return new Page(pageNum,totalCount,records);
    }
}
