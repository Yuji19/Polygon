package com.yuji.polygon.entity;


import javax.validation.constraints.NotNull;

/**
 * @ClassName Anounce
 * @Description TODO
 * @Author yuji
 * @Date 2020/8/4 17:25
 * @Version 1.0
 */

public class Anounce {
    private int id;

    @NotNull(message = "文档编号不能为空")
    private String fileNo;

    @NotNull(message = "文档名称不能为空")
    private String fileName;

    @NotNull(message = "文档类型不能为空")
    private String fileType;

    @NotNull(message = "下发日期不能为空")
    private String issueDate;

    @NotNull(message = "科室不能为空")
    private String editOffice;

    @NotNull(message = "编辑者不能为空")
    private String editPerson;

    private String oldFileNo;

    private String note;

    @NotNull(message = "文档路径不能为空")
    private String filePath;

    private String newVersion ;

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
}
