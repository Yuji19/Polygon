package com.yuji.polygon.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @interface: PermissionMenuMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 16:32:00
 */

@Mapper
public interface PermissionMenuMapper {

    int insertPermissionMenu(int pid, @Param("mids") int[] mids);

    int deletePermissionMenuByPid(int pid);

    int deletePermissionMenuByMid(int mid);
}
