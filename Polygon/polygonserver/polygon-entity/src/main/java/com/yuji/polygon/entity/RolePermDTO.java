package com.yuji.polygon.entity;

import java.util.List;

/**
 * @className: PermMenuVO
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-14 17:27:00
 */
public class RolePermDTO {

    private Role role;

    private List<Permission> permissions;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "PermMenuDTO{" +
                "role=" + role +
                ", permissions=" + permissions +
                '}';
    }
}
