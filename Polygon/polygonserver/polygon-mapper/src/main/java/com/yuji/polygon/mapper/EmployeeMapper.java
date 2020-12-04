package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    
    Employee getEmployeeByEid(int eid);

    int countTotalEmployee(Employee employee);

    List<Employee> getAllEmployee(@Param("employee") Employee employee,
                                  @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int deleteEmployeeById(int id);

    int updateEmployee(Employee employee);
}
