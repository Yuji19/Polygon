package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.EmployeeMapper;
import com.yuji.polygon.mapper.EmployeeRoleMapper;
import com.yuji.polygon.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
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
    OperationService operationService;

    @Autowired
    EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    SessionRegistry sessionRegistry;

    @Value("${custom.initial-password}")
    private String initialPassword;


    @Override
    public UserDetails loadUserByUsername(String employeeNo) throws UsernameNotFoundException {
        Employee employee = employeeMapper.getEmployeeByNo(employeeNo);
        if (employee == null){
            throw new UsernameNotFoundException(String.format("No user found with username: %s", employeeNo));
        }

        List<Role> roles = roleService.getRoleByEmployeeId(employee.getId());
        employee.setRoles(roles);
        return employee;
    }


    /**
     * 增加员工以及该员工所拥有的角色
     * @param employee
     * @param rids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "employee_page_cache",allEntries = true)
    public int insertEmployee(Employee employee,int[] rids){

        employee.setPassword(initialPassword);
        employee.setEnabled(true);
        employee.setGmtCreate(new Date());
        employee.setGmtModified(new Date());
        int result1 = employeeMapper.insertEmployee(employee);
        int result2 = employeeRoleMapper.insertEmployeeRole(employee.getId(),rids);

        return (result1 > 0 && result2 > 0) ? 1:0;
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @Caching(evict = {@CacheEvict(value ="employee_cache" ,key = "'EmployeeService.getEmployeeByEid_'+#id"),@CacheEvict(value ="employee_page_cache" ,allEntries = true)})
    public int deleteEmployeeById(int id){
        return employeeMapper.deleteEmployeeById(id);
    }

    /**
     * 增加员工的角色
     * @param eid
     * @param rids
     * @return
     */
    @CachePut(value = "employee_cache" ,key = "'EmployeeService.getEmployeeByEid_'+#eid")
    public int insertEmpolyeeRole(int eid, int[] rids){
        return employeeRoleMapper.insertEmployeeRole(eid,rids);
    }

    /**
     * 删除员工拥有的某些角色
     * @param eid
     * @param rids
     * @return
     */
    @CachePut(value ="employee_cache" ,key = "'EmployeeService.getEmployeeByEid_'+#eid")
    public int deleteEmpolyeeRole(int eid, int[] rids){
        return employeeRoleMapper.deleteEmployeeRole(eid,rids);
    }

    /**
     * 更新员工基本信息
     * @param employee
     * @return
     */
    @Caching(put = {@CachePut(value = "employee_cache" ,key = "'EmployeeService.getEmployeeByNo_'+#employee.no"),@CachePut(value = "employee_cache" ,key = "'EmployeeService.getEmployeeByEid_'+#employee.id")})
    public int updateEmployee(Employee employee){
        employee.setGmtModified(new Date());
        Employee principal = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(employee.getDepartmentId() == principal.getDepartmentId()
                || CommonUtil.checkRole(principal.getRoles(),"admin") ){
            return employeeMapper.updateEmployee(employee);
        }else {
            return 0;
        }

    }

    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    @CachePut(value = "employee_cache" ,key = "'EmployeeService.getEmployeeByEid_'+#id")
    public int updatePassword(int id, String password){
        //可在此添加加密...
        return employeeMapper.updatePassword(id,password,new Date());
    }

    @CachePut(value = "employee_cache" ,key = "'EmployeeService.getEmployeeByEid_'+#id")
    public int updateEnabled(int id, boolean enabled){
        return employeeMapper.updateEnabled(id,enabled,new Date());
    }

    /**
     * 根据员工编号获取员工
     * @param no
     * @return
     */
    @Cacheable(value = "employee_cache" ,key = "'EmployeeService.getEmployeeByNo_'+#no")
    public Employee getEmployeeByNo(String no){
        return employeeMapper.getEmployeeByNo(no);
    }

    @Cacheable(value = "employee_cache" ,key = "'EmolyeeService.getEmployeeByNos_'+#nos")
    public List<Employee> getEmployeeByNos(String[] nos){
        return employeeMapper.getEmployeeByNos(nos);
    }

    @Cacheable(value = "employee_cache" ,key = "'EmployeeService.getEmployeeByEid_'+#eid")
    public Employee getEmployeeByEid(int eid){
        return employeeMapper.getEmployeeByEid(eid);
    }

    @Cacheable(value = "employee_page_cache" ,key = "'EmployeeService.getEmployeeByRoleAndDept_'+#rid+#deptId")
    public List<EmployeeVO> getEmployeeByRoleAndDept(int rid, int deptId){
        return employeeMapper.getEmployeeByRoleAndDept(rid,deptId);
    }

    /**
     * 分页查询
     * @param employee
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "employee_page_cache", key = "'EmployeeService.getAllEmployee_'+#p0+#p1+#p2")
    public Page<Employee> getAllEmployee(Employee employee, int pageNum, int pageSize){
        int startIndex = (pageNum-1)*pageSize;
        int totalCount = employeeMapper.countTotalEmployee(employee);
        List<EmployeeVO> records = employeeMapper.getAllEmployee(employee,startIndex,pageSize);
        return new Page(pageNum,totalCount,records);
    }
}
