package com.yuji.polygon.controller;

import com.yuji.polygon.entity.Employee;
import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.service.MenuService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public String addEmployee(@Valid Employee employee, int[] rids){
        int result = employeeService.insertEmployee(employee,rids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @PostMapping("/add/role")
    public String addEmployeeRole(int eid, int[] rids){
        int result = employeeService.insertEmpolyeeRole(eid,rids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @DeleteMapping("/delete/{eid}")
    public String deleteEmployee(@PathVariable int eid){
        int result = employeeService.deleteEmployeeById(eid);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

    @DeleteMapping("/delete/{eid}/{rids}")
    public String deleteEmployeeRole(@PathVariable int eid, @PathVariable int[] rids){
        int result = employeeService.deleteEmpolyeeRole(eid,rids);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

    @PutMapping("/update")
    public String updateEmployee(@RequestBody @Valid Employee employee){
        int result = employeeService.updateEmployee(employee);
        return result > 0 ? ConstantValue.UPDATE_SUCCESS : ConstantValue.UPDATE_FAILURE;
    }

    @PutMapping("/query/page")
    public Page getAllEmployee(Employee employee, Integer pageNum, Integer pageSize){
        System.out.println("employeeNo: "+employee.getEmployeeNo());
        return employeeService.getAllEmployee(employee,pageNum,pageSize);
    }

    @GetMapping("/query/current")
    public Map<String,Object> getCurrentEmployee(Authentication authentication){
        Map<String,Object> map = new HashMap<>(4);
        Employee employee = new Employee();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        employee.setEmployeeNo(userDetails.getUsername());
        Collection<SimpleGrantedAuthority> collection = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
        employee = employeeService.getEmployeeByEmployeeNo(employee.getEmployeeNo());
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

}
