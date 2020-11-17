package com.yuji.polygon.service;

import com.yuji.polygon.entity.Department;
import com.yuji.polygon.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @className: DepartmentService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-17 10:52:00
 */

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    public int insertDepartment(Department department){
        return departmentMapper.insertDepartment(department);
    }

    public Department getDepartmentById(int id){
        return departmentMapper.getDepartmentById(id);
    }

    public List<Department> getAllDepartment(){
        return departmentMapper.getAllDepartment();
    }

    public int deleteDepartmentById(int id){
        return departmentMapper.deleteDepartmentById(id);
    }

    public int updateDepartment(Department department){
        department.setGmtModified(new Date());
        return departmentMapper.updateDepartment(department);
    }
}
