package com.yuji.polygon.entity;

/**
 * @className: EmplRoleDTO
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-16 09:17:00
 */
public class EmplRoleDTO {

    private Employee employee;
    private int[] rids;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int[] getRids() {
        return rids;
    }

    public void setRids(int[] rids) {
        this.rids = rids;
    }
}
