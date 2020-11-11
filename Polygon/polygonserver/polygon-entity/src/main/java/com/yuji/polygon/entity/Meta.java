package com.yuji.polygon.entity;

/**
 * @className: Meta
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-11 09:37:00
 */
public class Meta {

    private int id;

    private boolean keepAlive;

    private boolean requireAuth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
