package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @interface: RoleMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-11 16:00:00
 */

@Mapper
public interface RoleMapper {

    int insertRole(String name);

    List<Role> getRoleByEmployeeId(String eid);
}
