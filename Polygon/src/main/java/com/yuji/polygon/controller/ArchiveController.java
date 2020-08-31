package com.yuji.polygon.controller;


import com.yuji.polygon.entity.Archive;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.service.impl.ArchiveServiceImpl;
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
    ArchiveServiceImpl archiveService;

    @PostMapping("/all")
    public Page<Archive> listArchive(@Valid Archive archive, Integer pageNum, Integer pageSize){
        return archiveService.ListArchive(archive,pageNum,pageSize);
    }

    @PostMapping("/add")
    public String insert(@Valid Archive archive, MultipartFile file){
        return archiveService.insertArchive(archive, file);
    }

    @GetMapping("/download")
    public String download(String fileNo, HttpServletResponse response){
        return archiveService.downloadArchive(fileNo, response);
    }

    @PostMapping("/update")
    public String update(Archive archive){
        return archiveService.updateArchiive(archive);
    }

}
