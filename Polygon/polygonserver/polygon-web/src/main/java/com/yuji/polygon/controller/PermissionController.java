package com.yuji.polygon.controller;

import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: PermissionController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-11 16:22:00
 */

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/query/all")
    public List getAllPermission(){
        List<List> result = new ArrayList<>();
        List<Permission> permissions1 = permissionService.getPermissionOfOperation();
        List<Permission> permissions2 = permissionService.getPermissionOfMenu();
        result.add(permissions1);
        result.add(permissions2);
        return result;
    }

    @GetMapping("/query/operation/{rid}")
    public List<Permission> getPermissionOfOperationByRid(@PathVariable("rid") Integer rid){
        return permissionService.getPermissionOfOperationByRid(rid);
    }

    @GetMapping("/query/menu/{rid}")
    public List<Permission> getPermissionOfMenuByRid(@PathVariable("rid") Integer rid){
        return permissionService.getPermissionOfMenuByRid(rid);
    }
}
