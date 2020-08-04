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

}
