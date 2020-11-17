package com.yuji.polygon.controller;

import com.yuji.polygon.entity.Employee;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @PostMapping("/add")
    public String addEmployee(@Valid Employee employee, int[] rids){
        int result = employeeService.insertEmployee(employee,rids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

}
