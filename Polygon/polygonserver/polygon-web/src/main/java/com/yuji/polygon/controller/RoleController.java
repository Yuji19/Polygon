package com.yuji.polygon.controller;

import com.yuji.polygon.entity.RolePermDTO;
import com.yuji.polygon.service.RoleService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String addRole(@RequestBody @Valid RolePermDTO rolePermDTO){
        int result = roleService.insertRole(rolePermDTO.getRole(), rolePermDTO.getPermissions());
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @PostMapping("/add/permission")
    public String addRolePermission(int rid, int[] pids){
        int result = roleService.insertRolePermission(rid,pids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @DeleteMapping("/delete/{rids}")
    public String deleteRole(@PathVariable int[] rids){
        int result = roleService.deleteRoleById(rids);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }


    @DeleteMapping("/delete/{rid}/{pids}")
    public String deleteRolePermission(@PathVariable int rid, @PathVariable int[] pids){
        int result = roleService.deleteRolePermissionByRidAndPid(rid,pids);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

}
