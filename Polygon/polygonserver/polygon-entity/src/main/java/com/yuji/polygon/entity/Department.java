package com.yuji.polygon.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @className: Department
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-17 10:34:00
 */
public class Department implements Serializable {

    private int id;

    private String name;

    private Date gmtCreate;

    private Date gmtModified;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
