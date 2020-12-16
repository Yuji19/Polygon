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

    @PostMapping("/add/one")
    public String addFlowSignFlow(@Valid FileSign fileSign, String[] eNo, MultipartFile file){
        if (eNo.length < ConstantValue.FILE_SING_AUDIT_LENGTH ){
            throw new APIException(ResultCode.VALIDATE_FAILED.getCode(),"签审者不足");
        }
        int result = fileSignService.addFlowSignFlow(fileSign,eNo,file);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @PutMapping("/update/one")
    public String updateFileSignFlow(@RequestBody @Valid Approve approve){
        int result = fileSignService.updateFileSignFlow(approve);
        return result > 0 ? ConstantValue.UPDATE_SUCCESS : ConstantValue.UPDATE_FAILURE;
    }

    @GetMapping("/query/page")
    public Page getFileSignPage(FileSign fileSign, int pageNum, int pageSize){
        return fileSignService.getFileSignPage(fileSign,pageNum,pageSize);
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
