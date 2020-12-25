package com.yuji.polygon.controller;

import com.yuji.polygon.entity.Page;
import com.yuji.polygon.entity.Role;
import com.yuji.polygon.service.RoleService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/add/one")
    public String addRole(@Valid Role role , int[] pids){
        int result = roleService.insertRole(role,pids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @GetMapping("/add/{rid}/{pids}")
    public String addRolePermission(@PathVariable("rid") Integer rid, @PathVariable("pids") int[] pids){
        int result = roleService.insertRolePermission(rid,pids);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @DeleteMapping("/delete/{rids}")
    public String deleteRole(@PathVariable int[] rids){
        int result = roleService.deleteRoleById(rids);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }


    @DeleteMapping("/delete/{rid}/{pids}")
    public String deleteRolePermission(@PathVariable Integer rid, @PathVariable int[] pids){
        int result = roleService.deleteRolePermissionByRidAndPid(rid,pids);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

    @PutMapping("/update/base")
    public String updateRoleBase(@RequestBody @Valid Role role){
        int result = roleService.updateRoleBase(role);
        return result > 0 ? ConstantValue.UPDATE_SUCCESS : ConstantValue.UPDATE_FAILURE;
    }

    @GetMapping("/query/eid/{eid}")
    public List<Role> getRoleByEmployeeId(@PathVariable int eid){
        return roleService.getRoleByEmployeeId(eid);
    }

    @PutMapping("/query/page")
    public Page getRolePage(String name, int pageNum, int pageSize){
        return roleService.getRolePage(name, pageNum, pageSize);
    }

    @GetMapping("/query/all")
    public List<Role> getAllRole(){
        return roleService.getAllRole();
    }

}
