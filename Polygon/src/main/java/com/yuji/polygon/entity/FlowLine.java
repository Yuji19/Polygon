package com.yuji.polygon.entity;

import java.util.Date;

/**
 * @className: FlowLine
 * @description: 流程线表
 * @author: yuji
 * @create: 2020-10-28 18:39:00
 */
public class FlowLine {

    private int id;

    private String flowNo;

    private int preNode;

    private int nextNode;

    private Date gmtCreate;

    private Date gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public int getPreNode() {
        return preNode;
    }

    public void setPreNode(int preNode) {
        this.preNode = preNode;
    }

    public int getNextNode() {
        return nextNode;
    }

    public void setNextNode(int nextNode) {
        this.nextNode = nextNode;
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
