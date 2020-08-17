package com.yuji.polygon.entity;


import javax.validation.constraints.NotEmpty;


/**
 * @className: Archive
 * @description: TODO
 * @author: yuji
 * @create: 2020-08-04 17:25
 */

public class Archive {
    private int id;

    @NotEmpty(message = "文档编号不能为空")
    private String fileNo;

    private String oldFileNo;

    @NotEmpty(message = "文档名称不能为空")
    private String fileName;

    private String filePath;

    @NotEmpty(message = "文档类型不能为空")
    private String fileType;

    @NotEmpty(message = "编辑科室不能为空")
    private String editOffice;

    @NotEmpty(message = "编辑者不能为空")
    private String editPerson;

    @NotEmpty(message = "下发日期不能为空")
    private String issueDate;

    private String note;

    private String newVersion ;

    //创建时间
    private String gmtCreate;

    //更新时间
    private String gmtModified;

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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
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

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
