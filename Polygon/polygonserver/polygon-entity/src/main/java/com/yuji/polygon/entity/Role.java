package com.yuji.polygon.entity;

import java.util.List;

/**
 * @className: Role
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-10 19:09:00
 */
public class Role {

    private int id;

    private String name;

    private List<Permission> permissions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
