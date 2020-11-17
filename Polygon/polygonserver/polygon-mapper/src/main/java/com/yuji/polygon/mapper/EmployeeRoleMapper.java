package com.yuji.polygon.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @className: EmpolyeeRoleMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 16:26:00
 */

@Mapper
public interface EmployeeRoleMapper {

    int insertEmployeeRole(int eid, @Param("rids") int[] rids);

    int deleteEmployeeRoleByEidAndRid(int eid, @Param("rids") int[] rids);
}
