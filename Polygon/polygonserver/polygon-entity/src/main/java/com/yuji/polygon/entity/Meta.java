package com.yuji.polygon.entity;

/**
 * @className: Meta
 * @description: 前端路由元信息
 * @author: yuji
 * @create: 2020-11-11 09:37:00
 */
public class Meta {

    //后端访问路径
    private String url;

    private String icon;

    private boolean keepAlive;

    private boolean requireAuth;

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
