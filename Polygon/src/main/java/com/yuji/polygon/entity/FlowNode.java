package com.yuji.polygon.entity;

import java.util.Date;

/**
 * @className: FlowNode
 * @description: 流程节点
 * @author: yuji
 * @create: 2020-10-28 18:44:00
 */
public class FlowNode {

    private int id;

    private String flowNo;

    private String flowNodeName;

    private int flowNodeRole;

    private String remark;

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

    public String getFlowNodeName() {
        return flowNodeName;
    }

    public void setFlowNodeName(String flowNodeName) {
        this.flowNodeName = flowNodeName;
    }

    public int getFlowNodeRole() {
        return flowNodeRole;
    }

    public void setFlowNodeRole(int flowNodeRole) {
        this.flowNodeRole = flowNodeRole;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
