package com.yuji.polygon;

import com.yuji.polygon.entity.Employee;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.service.PermissionService;
import com.yuji.polygon.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;

import java.util.Date;
import java.util.List;

@SpringBootTest
class PolygonWebApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    @Test
    void contextLoads() {

    }

    @Test
    void testPermission(){

    }

    @Test
    void testAddEmployee(){
        Employee employee = new Employee();
        employee.setNo("GREE1001");
        employee.setName("员工3");
        employee.setEnabled(false);
        employee.setPassword("123456");
        employee.setGmtCreate(new Date());
        employee.setGmtModified(new Date());
        int[] rids = {1,2};
        int result = employeeService.insertEmployee(employee,rids);
        System.out.println(result);
    }

    @Test
    void testAddEmpolyeeRole(){
        int eid = 2;
        int[] rids = {1,2};
        int result = employeeService.insertEmpolyeeRole(eid,rids);
        System.out.println(result);
    }

    @Test
    void testDeleteEmployeeRole(){
        int eid = 2;
        int[] rids = {1,2};
        int result = employeeService.deleteEmpolyeeRole(eid,rids);
        System.out.println(result);
    }

    @Test
    void testAddRole(){

    }

    @Test
    void testAddRolePermission(){
        int rid = 10;
        int[] pids = {1,3};
        int result = roleService.insertRolePermission(rid,pids);
        System.out.println(result);
    }

    @Test
    void testDeleteRolePermission(){
        int rid = 1;
        int[] pids = {1,3};
        int result = roleService.deleteRolePermissionByRidAndPid(rid,pids);
        System.out.println(result);
    }

    @Test
    void testGetAllEmployee(){
        Employee employee = new Employee();
        employee.setNo("GREE1002");
        Page page = employeeService.getAllEmployee(employee,1,10);
        System.out.println(page);
    }
}
