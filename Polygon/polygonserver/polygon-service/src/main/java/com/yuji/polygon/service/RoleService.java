package com.yuji.polygon.service;

import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.entity.Role;
import com.yuji.polygon.mapper.OperationMapper;
import com.yuji.polygon.mapper.RoleMapper;
import com.yuji.polygon.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @className: RoleService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-12 10:03:00
 */

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    PermissionService permissionService;

    @Autowired
    OperationMapper permissionMenuMapper;

    /**
     * 查询员工所拥有的角色
     * @param eid
     * @return
     */
    public List<Role> getRoleByEmployeeId(int eid){
        return roleMapper.getRoleByEmployeeId(eid);
    }

    /**
     * 增加角色
     * @param role
     * @param permissions
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(Role role, List<Permission> permissions){
        role.setName("ROLE_"+role.getName());
        role.setGmtCreate(new Date());
        role.setGmtModified(new Date());
        int result1 = roleMapper.insertRole(role);
        int result2 = 0;
        int[] pids = new int[permissions.size()];
        for (int i = 0; i < permissions.size(); i++){
            Permission permission = permissions.get(i);
            pids[i] = permission.getId();
        }
        if (pids.length > 0){
            result2 = rolePermissionMapper.insertRolePermission(role.getId(),pids);
        }
        return (result1 > 0 && result2 > 0 ) ? 1 : 0;
    }

    public int deleteRoleById(int[] rids){
        return roleMapper.deleteRoleById(rids);
    }

    /**
     * 增加角色权限
     * @param rid
     * @param pids
     * @return
     */
    public int insertRolePermission(int rid, int[] pids){
        return rolePermissionMapper.insertRolePermission(rid,pids);
    }

    /**
     * 删除角色的权限
     * @param rid
     * @param pids
     * @return
     */
    public int deleteRolePermissionByRidAndPid(int rid, int[] pids){
        return rolePermissionMapper.deleteRolePermissionByRidAndPid(rid,pids);
    }

    /**
     * 分页查询
     * @param nameZh
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<Role> getRolePage(String nameZh, int pageNum, int pageSize){
        int startIndex = (pageNum-1)*pageSize;
        int totalCount = roleMapper.countTotalRole(nameZh);
        List<Role> records = roleMapper.getRolePage(nameZh,startIndex,pageSize);
        return new Page(pageNum,totalCount,records);
    }

    public List<Role> getAllRole(){
        return roleMapper.getAllRole();
    }

}
