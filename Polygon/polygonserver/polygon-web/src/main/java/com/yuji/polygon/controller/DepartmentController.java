package com.yuji.polygon.controller;

import com.yuji.polygon.entity.Department;
import com.yuji.polygon.service.DepartmentService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @className: DepartmentController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-17 10:55:00
 */

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/add/one")
    public String addDepartment(@RequestBody @Valid Department department){
        int result = departmentService.insertDepartment(department);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @GetMapping("/query/{id}")
    public Department getDepartmentById(@PathVariable int id){
        return departmentService.getDepartmentById(id);
    }

    @GetMapping("/query/all")
    public List<Department> getAllDepartment(){
        return  departmentService.getAllDepartment();
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartmentById(@PathVariable int id){
        int result = departmentService.deleteDepartmentById(id);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

    @PutMapping("/update/one")
    public String updateDepartment(@RequestBody @Valid Department department){
        int result = departmentService.updateDepartment(department);
        return result > 0 ? ConstantValue.UPDATE_SUCCESS : ConstantValue.UPDATE_FAILURE;
    }
}
