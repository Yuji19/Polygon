package com.yuji.polygon.entity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @className: Permission
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-13 15:43:00
 */
public class Permission {

    private int id;

    @NotNull(message = "操作权限名称不能为空")
    private String name;

    private List<Menu> menus;


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

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menus=" + menus +
                '}';
    }
}
