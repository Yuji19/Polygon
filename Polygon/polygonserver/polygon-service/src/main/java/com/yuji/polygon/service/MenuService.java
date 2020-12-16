package com.yuji.polygon.service;

import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: MenuService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-11 16:32:00
 */

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    public List<Menu> getMenuByEmployeeId(int eid){
        return menuMapper.getMenuByEmployeeId(eid);
    }

    public List<Menu> getAllMenuWithPermission(){
        return menuMapper.getAllMenuWithPermission();
    }

    public List<Menu> getMenuWithPermissionByRoleId(int rid){
        return menuMapper.getMenuWithPermissionByRoleId(rid);
    }

    public List<Menu> getAllMenu(){
        return menuMapper.getAllMenu();
    }

    public List<Menu> getMenuByRoleNames(String[] roleNames){
        return menuMapper.getMenuByRoleNames(roleNames);
    }
}
