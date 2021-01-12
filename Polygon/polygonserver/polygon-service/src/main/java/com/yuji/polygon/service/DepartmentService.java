package com.yuji.polygon.service;

import com.yuji.polygon.entity.Department;
import com.yuji.polygon.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
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
@CacheConfig(cacheNames = "department_cache")
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;


    @CacheEvict(key = "'DepartmentService.getAllDepartment'")
    public int insertDepartment(Department department){
        return departmentMapper.insertDepartment(department);
    }

    @Cacheable(key = "'DepartmentService.getDepartmentById_'+#id")
    public Department getDepartmentById(int id){
        return departmentMapper.getDepartmentById(id);
    }

    @Cacheable(key = "'DepartmentService.getAllDepartment'")
    public List<Department> getAllDepartment(){
        return departmentMapper.getAllDepartment();
    }

    @Caching(evict = {@CacheEvict(key="'DepartmentService.getAllDepartment'"),@CacheEvict(key="'DepartmentService.getDepartmentById_'+#id")})
    public int deleteDepartmentById(int id){
        return departmentMapper.deleteDepartmentById(id);
    }

    @CachePut(key = "'DepartmentService.updateDepartment_'+department")
    public int updateDepartment(Department department){
        department.setGmtModified(new Date());
        return departmentMapper.updateDepartment(department);
    }
}
