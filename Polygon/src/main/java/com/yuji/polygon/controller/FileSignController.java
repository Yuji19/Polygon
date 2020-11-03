package com.yuji.polygon.controller;

import com.yuji.polygon.entity.Audit;
import com.yuji.polygon.entity.FileSign;
import com.yuji.polygon.entity.FlowNode;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.service.FileSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @className: FileSignController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-03 15:20:00
 */

@RestController
@RequestMapping("/fileSign")
public class FileSignController {

    @Autowired
    FileSignService fileSignService;

    @PostMapping("/add")
    public String addFlowSignFlow(@Valid FileSign fileSign, @Valid FlowNode firstNode, @Valid FlowNode secondNode,
                                  FlowNode thirdNode, MultipartFile file){
        return fileSignService.addFlowSignFlow(fileSign,firstNode,secondNode,thirdNode,file);
    }

    @PostMapping("/update")
    public String updateFileSignFlow(@RequestBody @Valid Audit audit){
        return fileSignService.updateFileSignFlow(audit);
    }

    @PostMapping("/page")
    public Page getFileSignPage(FileSign fileSign, int currentPageNumber, int pageSize){
        return fileSignService.getFileSignPage(fileSign,currentPageNumber,pageSize);
    }

    @GetMapping("/download")
    public void downloadFileSign(int id, HttpServletResponse response){
        fileSignService.downloadSignFile(id,response);
    }
}
