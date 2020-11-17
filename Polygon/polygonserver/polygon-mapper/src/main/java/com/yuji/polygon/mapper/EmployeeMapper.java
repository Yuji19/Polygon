package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface: EmployeeMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-12 10:11:00
 */

@Mapper
public interface EmployeeMapper {

    int insertEmployee(Employee employee);

    Employee getEmployeeByEmployeeNo(String employeeNo);

    int deleteEmployeeById(int id);

    int updateEmployee(Employee employee);
}
