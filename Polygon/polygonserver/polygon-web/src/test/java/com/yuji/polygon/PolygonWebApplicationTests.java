package com.yuji.polygon;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PolygonWebApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ArchiveService archiveService;

    @Test
    void contextLoads() {

    }

    @Test
    void testPermission(){

    }

    @Test
    void testAddEmployee(){
        Employee employee = new Employee();
        employee.setNo("G1001");
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
        employee.setNo("G1002");
        Page page = employeeService.getAllEmployee(employee,1,10);
        System.out.println(page);
    }

    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @Test
    void testSend(){
        Map<String,Object> obj = new HashMap<>();

        rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,
                MailConstants.MAIL_ROUTING_KEY_NAME,obj,new CorrelationData("uuuururrr"));
    }
    
    @Test
    void testCache(){
        for (int i = 0; i < 2; i++){
            //employeeService.getEmployeeByNo("yg1003");
            Page<Employee> result = employeeService.getAllEmployee(new Employee(),1,10);
            System.out.println("count == "+result.getTotalCount());

        }
        //employeeService.getAllEmployee(new Employee(),1,10);
//        Employee employee = new Employee();
//        employee.setNo("yg1009");
//        employee.setName("员工9");
//        employee.setDepartmentId(1);
//        int[] rids = {12};
//        employeeService.insertEmployee(employee,rids);
        //employeeService.deleteEmployeeById(12);
    }

    @Test
    void testCache2(){
        for (int i = 0; i < 2; i++){
            archiveService.getAllArchive(new Archive(),1,10);
        }
    }

    @Test
    void testCache3(){
//        for (int i = 0; i < 2; i++){
//            roleService.getAllRole();
//        }
//        Role role = new Role();
//        role.setName("测试1");
//        int[] pids = {13};
//        roleService.insertRole(role,pids);
        roleService.getAllRole();
        //roleService.deleteRoleById(new int[]{15});
    }
}
