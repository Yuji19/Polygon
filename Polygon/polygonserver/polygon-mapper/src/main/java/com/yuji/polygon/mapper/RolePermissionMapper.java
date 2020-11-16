package com.yuji.polygon.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @interface: RolePermissionMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 16:30:00
 */

@Mapper
public interface RolePermissionMapper {

    int insertRolePermission(int rid, @Param("pids") int[] pids);


    int deleteRolePermissionByRidAndPid(int rid, @Param("pids") int[] pids);
}
