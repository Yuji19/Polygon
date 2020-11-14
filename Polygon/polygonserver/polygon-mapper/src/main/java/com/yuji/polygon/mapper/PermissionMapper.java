package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @className: PermissionMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 10:12:00
 */

@Mapper
public interface PermissionMapper {

    List<Permission> getPermissionByRoleId(int rid);

    List<Permission> getAllPermissionByRole();
}
