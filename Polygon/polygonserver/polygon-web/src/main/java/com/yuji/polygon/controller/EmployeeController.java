package com.yuji.polygon.controller;

import com.yuji.polygon.config.MySessionManage;
import com.yuji.polygon.entity.Employee;
import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.service.MenuService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

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
    EmployeeService employeeService;

    @Autowired
    MenuService menuService;

    @Autowired
    MySessionManage sessionManage;

    @Autowired
    SessionRegistry sessionRegistry;

    @PostMapping("/add")
    public String addEmployee(@Valid Employee employee, int[] rids){
        int result = employeeService.insertEmployee(employee,rids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @GetMapping("/add/{eid}/{rids}")
    public String addEmployeeRole(@PathVariable int eid, @PathVariable int[] rids,Authentication authentication){
        System.out.println("add role");
        int result = employeeService.insertEmpolyeeRole(eid,rids);
        if (result > 0){
//            Employee employee = (Employee) authentication.getPrincipal();
//            //更新当前用户权限
//            if(employee.getId() == eid){
//                employee = (Employee) employeeService.loadUserByUsername(employee.getEmployeeNo());
//                SecurityContextHolder.getContext().
//                        setAuthentication(new UsernamePasswordAuthenticationToken(employee, authentication.getCredentials(),employee.getAuthorities()));
//            }
            updateAuthentication(eid);

            return ConstantValue.ADD_SUCCESS;
        }
        return ConstantValue.ADD_FAILURE;
    }

    @DeleteMapping("/delete/{eid}")
    public String deleteEmployee(@PathVariable int eid){
        int result = employeeService.deleteEmployeeById(eid);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

    @DeleteMapping("/delete/{eid}/{rids}")
    public String deleteEmployeeRole(@PathVariable int eid, @PathVariable int[] rids,Authentication authentication){
        System.out.println("delete role");
        int result = employeeService.deleteEmpolyeeRole(eid,rids);
        if (result > 0){
//            Employee employee = (Employee) authentication.getPrincipal();
//            //更新当前用户权限
//            if(employee.getId() == eid){
//                employee = (Employee) employeeService.loadUserByUsername(employee.getEmployeeNo());
//                SecurityContextHolder.getContext().
//                        setAuthentication(new UsernamePasswordAuthenticationToken(employee, authentication.getCredentials(),employee.getAuthorities()));
//            }

            updateAuthentication(eid);
            return ConstantValue.DELETE_SUCCESS;
        }
        return ConstantValue.DELETE_FAILURE;
    }

    @PutMapping("/update")
    public String updateEmployee(@RequestBody @Valid Employee employee,Authentication authentication){
        int result = employeeService.updateEmployee(employee);
        if (result > 0){
            //update session
            return ConstantValue.UPDATE_SUCCESS;
        }
        return ConstantValue.UPDATE_FAILURE;
    }

    @PutMapping("/query/page")
    public Page getAllEmployee(Employee employee, Integer pageNum, Integer pageSize){
        System.out.println("employeeNo: "+employee.getEmployeeNo());
        return employeeService.getAllEmployee(employee,pageNum,pageSize);
    }

    /**
     * 获取当前用户，该访问不需要权限，所以不加/query
     * @param 
     * @return
     */
    @GetMapping("/current")
    public Map<String,Object> getCurrentEmployee(){
        System.out.println("fetch curren");
        Map<String,Object> map = new HashMap<>(4);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = (Employee) authentication.getPrincipal();
        System.out.println("current: "+employee);
        Collection<SimpleGrantedAuthority> collection = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
        List<String> permissions = new ArrayList<>();
        for (SimpleGrantedAuthority authority : collection){
            permissions.add(authority.getAuthority());
        }
        List<Menu> menus = menuService.getMenuByEmployeeId(employee.getId());
        map.put("employee",employee);
        map.put("permissions",permissions);
        map.put("menus",menus);
        return map;
    }

    public void updateAuthentication(int eid){
        Employee employee = employeeService.getEmployeeByEid(eid);
        //fetch all
        List<SessionInformation> list = sessionRegistry.getAllSessions(employee,false);
        for(SessionInformation sessionInformation : list){
            HttpSession session = sessionManage.getSession(sessionInformation.getSessionId());
            SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            Authentication authentication = securityContext.getAuthentication();
            Employee principal = (Employee) employeeService.loadUserByUsername(employee.getEmployeeNo());
            securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(principal,authentication.getCredentials(), principal.getAuthorities()));

        }
    }

}
