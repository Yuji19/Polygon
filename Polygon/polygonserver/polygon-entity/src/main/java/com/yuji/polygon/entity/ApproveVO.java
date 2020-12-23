package com.yuji.polygon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @className: ApproveVO
 * @description: TODO
 * @author: yuji
 * @create: 2020-12-22 19:26:49
 */
public class ApproveVO {

    private int id;

    private String flowNo;

    private int businessNO;

    private int flowNodeNo;

    private String flowNodeName;

    private String approveNo;

    private String approveName;

    private String approveInfo;

    private String approveState;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private Date approveDate;

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

    public int getBusinessNO() {
        return businessNO;
    }

    public void setBusinessNO(int businessNO) {
        this.businessNO = businessNO;
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

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    public String getApproveInfo() {
        return approveInfo;
    }

    public void setApproveInfo(String approveInfo) {
        this.approveInfo = approveInfo;
    }

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
}
