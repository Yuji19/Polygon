package com.yuji.polygon.entity;

import java.util.Date;

/**
 * @className: Audit
 * @description: 审批记录
 * @author: yuji
 * @create: 2020-10-28 18:46:00
 */
public class Audit {

    private int id;

    private int businessNo;

    private int flowNodeNo;

    private String employeeNo;

    private String employeeName;

    private String auditInfo;

    private int auditState;

    private Date auditDate;

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

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeNamel) {
        this.employeeName = employeeNamel;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", businessNo='" + businessNo + '\'' +
                ", flowNodeNo=" + flowNodeNo +
                ", employeeNo='" + employeeNo + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", auditInfo='" + auditInfo + '\'' +
                ", auditState=" + auditState +
                ", auditDate=" + auditDate +
                '}';
    }
}
