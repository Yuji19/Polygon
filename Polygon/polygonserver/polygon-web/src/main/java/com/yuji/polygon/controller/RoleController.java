package com.yuji.polygon.controller;

import com.yuji.polygon.entity.RolePermDTO;
import com.yuji.polygon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: RoleController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-16 09:45:00
 */

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    public String addRole(@RequestBody RolePermDTO rolePermDTO){
        int result = roleService.insertRole(rolePermDTO.getRole(), rolePermDTO.getPermissions());
        return result > 0 ? "提交成功":"提交失败";
    }
}
