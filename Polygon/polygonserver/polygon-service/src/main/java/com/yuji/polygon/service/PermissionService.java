package com.yuji.polygon.service;

import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.mapper.PermissionMapper;
import com.yuji.polygon.mapper.PermissionMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: PermissionService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 10:25:00
 */

@Service
public class PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    PermissionMenuMapper permissionMenuMapper;

    public List<Permission> getPermissionByRoleId(int rid){
        return permissionMapper.getPermissionByRoleId(rid);
    }

    public List<Permission> getAllPermissionByRole(){
        return permissionMapper.getAllPermissionByRole();
    }

}
