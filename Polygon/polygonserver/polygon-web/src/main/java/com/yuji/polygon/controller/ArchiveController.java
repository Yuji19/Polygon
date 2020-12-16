package com.yuji.polygon.controller;


import com.yuji.polygon.entity.Archive;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.service.ArchiveService;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @className: ArchiveController
 * @description: TODO
 * @author: yuji
 * @create: 2020-08-04 17:25
 */

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    ArchiveService archiveService;

    @PutMapping("/query/page")
    public Page<Archive> listArchive(Archive archive, Integer pageNum, Integer pageSize){
        return archiveService.ListArchive(archive,pageNum,pageSize);
    }

    @PostMapping("/add/one")
    public String insert(@Valid Archive archive, MultipartFile file){
        int result = archiveService.insertArchive(archive, file);
        return result > 0 ? ConstantValue.ADD_SUCCESS : ConstantValue.ADD_FAILURE;
    }

    @GetMapping("/download/{fileNo}")
    public void download(@PathVariable String fileNo, HttpServletResponse response){
        archiveService.downloadArchive(fileNo, response);
    }

    @PutMapping("/update/one")
    public String update(@RequestBody @Valid Archive archive){
        int result = archiveService.updateArchive(archive);
        return result > 0 ? ConstantValue.UPDATE_SUCCESS : ConstantValue.UPDATE_FAILURE;
    }

    @DeleteMapping("/delete/{aids}")
    public String delete(@PathVariable Long[] aids){
        int result = archiveService.deleteArchive(aids);
        return result > 0 ? ConstantValue.DELETE_SUCCESS : ConstantValue.DELETE_FAILURE;
    }

}
