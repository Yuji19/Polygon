package com.yuji.polygon.service;

import com.yuji.polygon.entity.Role;
import com.yuji.polygon.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Role> getRoleByEmployeeId(int eid){
        return roleMapper.getRoleByEmployeeId(eid);
    }
}
