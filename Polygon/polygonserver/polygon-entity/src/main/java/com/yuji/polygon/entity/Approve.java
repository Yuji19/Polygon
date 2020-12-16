package com.yuji.polygon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @className: Audit
 * @description: 审批记录
 * @author: yuji
 * @create: 2020-10-28 18:46:00
 */
public class Approve {

    private int id;

    private int businessNo;

    @NotNull(message = "流程节点编号不能为空")
    private int flowNodeNo;

    @NotNull(message = "审批者员工编号不能为空")
    private String approveNo;

    @NotNull(message = "审批者员工编号不能为空")
    private String approveName;

    private String approveInfo;

    @NotNull(message = "审批状态不能为空")
    private int approveState;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private Date approveDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(int businessNo) {
        this.businessNo = businessNo;
    }

    public int getFlowNodeNo() {
        return flowNodeNo;
    }

    public void setFlowNodeNo(int flowNodeNo) {
        this.flowNodeNo = flowNodeNo;
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

    public int getApproveState() {
        return approveState;
    }

    public void setApproveState(int approveState) {
        this.approveState = approveState;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
}
