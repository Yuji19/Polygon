package com.yuji.polygon.service;

import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.mapper.PermissionMapper;
import com.yuji.polygon.mapper.OperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: PermissionService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 10:25:00
 */

@Service
@CacheConfig(cacheNames = "permission_cache")
public class PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    public int insertPermission(Permission permission){
        return permissionMapper.insertPermission(permission);
    }

    public int deletePermissionById(int id){
        return deletePermissionById(id);
    }

    @Cacheable
    public List<Permission> getPermissionOfOperation(){
        return permissionMapper.getPermissionOfOperation();
    }

    @Cacheable
    public List<Permission> getPermissionOfOperationByRid(int rid){
        return permissionMapper.getPermissionOfOperationByRid(rid);
    }

    @Cacheable
    public List<Permission> getPermissionOfMenu(){
        return permissionMapper.getPermissionOfMenu();
    }

    @Cacheable
    public List<Permission> getPermissionOfMenuByRid(int rid){
        return permissionMapper.getPermissionOfMenuByRid(rid);
    }

}
