package com.yuji.polygon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @className: FlowVO
 * @description: TODO
 * @author: yuji
 * @create: 2020-12-22 10:09:11
 */
public class FlowVO {

    private String flowNo;

    private String flowName;

    private int businessNo;

    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private Date gmtCreate;

    private int flowNodeNo;

    private String flowNodeName;

    private String approveName;

    private String flowState;

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public int getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(int businessNo) {
        this.businessNo = businessNo;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public int getFlowNodeNo() {
        return flowNodeNo;
    }

    public void setFlowNodeNo(int flowNodeNo) {
        this.flowNodeNo = flowNodeNo;
    }

    public String getFlowNodeName() {
        return flowNodeName;
    }

    public void setFlowNodeName(String flowNodeName) {
        this.flowNodeName = flowNodeName;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }
}
