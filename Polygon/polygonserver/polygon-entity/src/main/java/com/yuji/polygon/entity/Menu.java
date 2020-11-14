package com.yuji.polygon.entity;

import java.util.List;

/**
 * @className: Menu
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-10 19:14:00
 */
public class Menu {

    private int id;

    private String name;

    private String url;

    private String path;

    private String icon;

    private String component;

    private Meta meta;

    private boolean enabled;

    private int parentId;

    private List<Menu> children;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }



    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", icon='" + icon + '\'' +
                ", component='" + component + '\'' +
                ", meta=" + meta +
                ", enabled=" + enabled +
                ", parentId=" + parentId +
                ", children=" + children +
                '}';
    }
}
