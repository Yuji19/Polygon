package com.yuji.polygon.controller;

import com.yuji.polygon.config.MySessionManage;
import com.yuji.polygon.entity.*;
import com.yuji.polygon.service.DepartmentService;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.service.MenuService;
import com.yuji.polygon.service.OperationService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: EmployeeController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-17 15:22:00
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    RoleHierarchy roleHierarchy;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MenuService menuService;

    @Autowired
    OperationService operationService;

    @Autowired
    MySessionManage sessionManage;

    @Autowired
    SessionRegistry sessionRegistry;

    @PostMapping("/add/one")
    public String addEmployee(@Valid Employee employee, int[] rids){
        int result = employeeService.insertEmployee(employee,rids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @GetMapping("/add/{eid}/{rids}")
    public String addEmployeeRole(@PathVariable int eid, @PathVariable int[] rids){
        int result = employeeService.insertEmpolyeeRole(eid,rids);
        if (result > 0){
            updateAuthentication(eid);

            return ConstantValue.ADD_SUCCESS;
        }
        return ConstantValue.ADD_FAILURE;
    }

    @DeleteMapping("/delete/{eid}")
    public String deleteEmployee(@PathVariable Integer eid){
        int result = employeeService.deleteEmployeeById(eid);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

    @DeleteMapping("/delete/{eid}/{rids}")
    public String deleteEmployeeRole(@PathVariable Integer eid, @PathVariable int[] rids){
        System.out.println("delete role");
        int result = employeeService.deleteEmpolyeeRole(eid,rids);
        if (result > 0){
            updateAuthentication(eid);
            return ConstantValue.DELETE_SUCCESS;
        }
        return ConstantValue.DELETE_FAILURE;
    }

    @PutMapping("/update/base")
    public String updateEmployee(@RequestBody @Valid Employee employee){
        int result = employeeService.updateEmployee(employee);
        if (result > 0){

            return ConstantValue.UPDATE_SUCCESS;
        }
        return ConstantValue.UPDATE_FAILURE;
    }

    @PutMapping("/update/password")
    public String updatePassword(Integer id, String password){
        int result = employeeService.updatePassword(id,password);
        if (result > 0){
            //可进行剔除用户
            return ConstantValue.UPDATE_SUCCESS;
        }
        return ConstantValue.UPDATE_FAILURE;
    }

    @PutMapping("/update/enabled")
    public String updateEnabled(Integer id, Boolean enabled){
        int result = employeeService.updateEnabled(id,enabled);
        if (result > 0){
            //可进行剔除用户
            return ConstantValue.UPDATE_SUCCESS;
        }
        return ConstantValue.UPDATE_FAILURE;
    }

    @PutMapping("/query/page")
    public Page getAllEmployee(Employee employee, Integer pageNum, Integer pageSize){
        return employeeService.getAllEmployee(employee,pageNum,pageSize);
    }

    /**
     * 获取审批人员，该访问不需要权限
     * @param rid
     * @param deptId
     * @return
     */
    @GetMapping("/fetch/{rid}/{deptId}")
    public List<EmployeeVO> getEmployeeByRoleAndDept(@PathVariable("rid") Integer rid, @PathVariable("deptId") Integer deptId){
        return employeeService.getEmployeeByRoleAndDept(rid,deptId);
    }

    /**
     * 获取当前用户，该访问不需要权限，所以不加/query
     * @param 
     * @return
     */
    @GetMapping("/current")
    public Map<String,Object> getCurrentEmployee(){
        Map<String,Object> map = new HashMap<>(4);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = (Employee) authentication.getPrincipal();
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employee,employeeVO);
        String departmentName = departmentService.getDepartmentById(employee.getDepartmentId()).getName();
        employeeVO.setDepartmentName(departmentName);
        //获取角色 包括继承的角色
        Collection<? extends GrantedAuthority> authorities = roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities());
        String[] roleNames;
        List<String> temp = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        roleNames = temp.toArray(new String[temp.size()]);
        List<String> permissions = new ArrayList<>();
        List<Operation> operations = operationService.getOperationByRoleNames(roleNames);
        temp.clear();
        temp = operations.stream().map(Operation::getName).collect(Collectors.toList());
        permissions.addAll(temp);

        List<Menu> menus = menuService.getMenuByRoleNames(roleNames);
        map.put("employee",employeeVO);
        map.put("permissions",permissions);
        map.put("menus",menus);
        return map;
    }

    public void updateAuthentication(Integer eid){
        Employee employee = employeeService.getEmployeeByEid(eid);

        List<SessionInformation> list = sessionRegistry.getAllSessions(employee,false);
        for(SessionInformation sessionInformation : list){
            HttpSession session = sessionManage.getSession(sessionInformation.getSessionId());
            SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            //获取当前身份令牌
            Authentication authentication = securityContext.getAuthentication();
            Employee principal = (Employee) employeeService.loadUserByUsername(employee.getNo());
            securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(principal,authentication.getCredentials(), principal.getAuthorities()));

        }
    }

}
