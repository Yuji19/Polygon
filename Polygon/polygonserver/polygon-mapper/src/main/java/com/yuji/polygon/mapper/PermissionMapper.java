package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Permission;
import org.apache.ibatis.annotations.Mapper;



/**
 * @className: PermissionMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 10:12:00
 */

@Mapper
public interface PermissionMapper {

    int insertPermission(Permission permission);

    int deletePermissionById(int id);
}
