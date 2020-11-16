package com.yuji.polygon;

import com.yuji.polygon.entity.Employee;
import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.service.PermissionService;
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

    @Test
    void contextLoads() {
        List<Permission> permissions = permissionService.getAllPermissionByRole();
        for (Permission permission : permissions){
            System.out.println(permission);
        }
    }

    @Test
    void testPermission(){
        List<Permission> permissions = permissionService.getPermissionByRoleId(1);
        for (Permission permission : permissions){
            System.out.println(permission);
        }
    }

    @Test
    void testAddEmployee(){
        Employee employee = new Employee();
        employee.setEmployeeNo("GREE1001");
        employee.setEmployeeName("员工2");
        employee.setEnabled(false);
        employee.setPassword("123456");
        employee.setGmtCreate(new Date());
        employee.setGmtModified(new Date());
        int[] rids = {2,3};
        int result = employeeService.insertEmployee(employee,rids);
        System.out.println(result);
    }

    @Test
    void testAddRole(){

    }

}
