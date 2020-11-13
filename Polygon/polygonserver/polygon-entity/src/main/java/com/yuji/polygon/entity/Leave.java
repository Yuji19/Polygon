package com.yuji.polygon.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @className: Leave
 * @description: 请假单实体
 * @author: yuji
 * @create: 2020-10-28 18:30:00
 */
public class Leave {

    private int id;

    @NotNull(message = "请假人员工编号不能为空")
    private String employeeNo;

    @NotNull(message = "请假人姓名不能为空")
    private String employeeName;

    @NotNull(message = "请假类型不能为空")
    private String leaveType;

    @NotNull(message = "请假原因不能为空")
    private String leaveReason;

    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private Date startDate;

    @NotNull(message = "结束日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private Date endDate;

    private String flowNo;

    private int flowState;

    private int currentNode;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "Asia/Shanghai")
    private Date gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public int getFlowState() {
        return flowState;
    }

    public void setFlowState(int flowState) {
        this.flowState = flowState;
    }

    public int getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
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
