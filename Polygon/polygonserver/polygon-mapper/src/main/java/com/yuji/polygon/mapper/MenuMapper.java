package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @interface: MenuMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-11 09:08:00
 */

@Mapper
public interface MenuMapper {

    int insertMenu(Menu menu);

    List<Menu> getMenuByEmployeeId(int eid);

    List<Menu> getAllMenuWithPermission();

    List<Menu> getMenuWithPermissionByRoleId(int rid);

    List<Menu> getAllMenu();

    List<Menu> getMenuByRoleNames(@Param("roleNames") String[] roleNames);
}
