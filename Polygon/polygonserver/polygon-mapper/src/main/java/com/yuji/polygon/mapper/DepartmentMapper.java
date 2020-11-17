package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @interface: DepartmentMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-17 10:41:00
 */

@Mapper
public interface DepartmentMapper {

    int insertDepartment(Department department);

    Department getDepartmentById(int id);

    List<Department> getAllDepartment();

    int deleteDepartmentById(int id);

    int updateDepartment(Department department);
}
