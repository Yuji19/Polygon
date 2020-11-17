package com.yuji.polygon.controller;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.service.FileSignService;
import com.yuji.polygon.utils.ConstantValue;
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
    public String addFlowSignFlow(@Valid FileSign fileSign, String[] eNo,String[] eName, MultipartFile file){
        if (eNo.length < ConstantValue.FILE_SING_AUDIT_LENGTH || eName.length < ConstantValue.FILE_SING_AUDIT_LENGTH){
            throw new APIException(ResultCode.VALIDATE_FAILED.getCode(),"签审者不足");
        }
        int result = fileSignService.addFlowSignFlow(fileSign,eNo,eName,file);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @PutMapping("/update")
    public String updateFileSignFlow(@RequestBody @Valid Audit audit){
        int result = fileSignService.updateFileSignFlow(audit);
        return result > 0 ? ConstantValue.UPDATE_SUCCESS : ConstantValue.UPDATE_FAILURE;
    }

    @GetMapping("/query/page")
    public Page getFileSignPage(FileSign fileSign, int currentPageNumber, int pageSize){
        return fileSignService.getFileSignPage(fileSign,currentPageNumber,pageSize);
    }

    @GetMapping("/download/{id}")
    public void downloadFileSign(@PathVariable int id, HttpServletResponse response){
        fileSignService.downloadSignFile(id,response);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFileSignFlow(@PathVariable int id){
        int result = fileSignService.deleteFileSignById(id);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }
}
