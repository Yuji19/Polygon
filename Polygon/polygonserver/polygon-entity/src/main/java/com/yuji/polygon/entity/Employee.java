package com.yuji.polygon.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @className: Employee
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 18:49:00
 */
public class Employee {

    private int id;

    @NotNull(message = "员工编号不能为空")
    private String employeeNo;

    @NotNull(message = "员工姓名不能为空")
    private String employeeName;

    @NotNull(message = "登录密码不能为空")
    private String password;

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

}
