package com.yuji.polygon.entity;

/**
 * @className: Permission
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-13 15:43:00
 */
public class Permission {

    private int id;

    private String name;

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

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
