package com.yuji.polygon.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @className: FileSign
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-02 18:36:00
 */
public class FileSign {

    private int id;

    @NotNull(message = "申请人员工编号不能为空")
    private String employeeNo;

    @NotNull(message = "申请人员工姓名不能为空")
    private String employeeName;

    @NotNull(message = "关联的项目编号不能为空")
    private String projectNo;

    private String filePath;

    private String flowNo;

    private int flowState;

    private int currentNode;

    private Date gmtCreate;

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

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
