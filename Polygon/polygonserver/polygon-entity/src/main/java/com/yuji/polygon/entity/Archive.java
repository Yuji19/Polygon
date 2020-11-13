package com.yuji.polygon.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @className: Archive
 * @description: TODO
 * @author: yuji
 * @create: 2020-08-04 17:25
 */

public class Archive {
    private int id;

    @NotNull(message = "文档编号不能为空")
    private String fileNo;

    private String oldFileNo;

    @NotNull(message = "文档名称不能为空")
    private String fileName;

    private String filePath;

    @NotNull(message = "文档类型不能为空")
    private String fileType;

    @NotNull(message = "编辑科室不能为空")
    private String editOffice;

    @NotNull(message = "编辑者不能为空")
    private String editPerson;

    @NotNull(message = "下发日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date issueDate;

    private String status;

    private String note;

    private String newVersion;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getEditOffice() {
        return editOffice;
    }

    public void setEditOffice(String editOffice) {
        this.editOffice = editOffice;
    }

    public String getEditPerson() {
        return editPerson;
    }

    public void setEditPerson(String editPerson) {
        this.editPerson = editPerson;
    }

    public String getOldFileNo() {
        return oldFileNo;
    }

    public void setOldFileNo(String oldFileNo) {
        this.oldFileNo = oldFileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }


}
