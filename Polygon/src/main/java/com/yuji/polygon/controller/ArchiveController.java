package com.yuji.polygon.controller;


import com.yuji.polygon.entity.APIException;
import com.yuji.polygon.entity.Archive;
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

    @GetMapping("/all")
    public String listArchives(Integer currentPageNum, Integer pageSize){
        //Archive archive = new Archive();
        //archive.setFileName("ceshi");
        //return archive;
        throw new APIException("测试抛出异常");
    }

    @PostMapping("/add")
    public String insert(@Valid Archive archive, MultipartFile file){
        return archiveService.insertArchive(archive, file);
    }

    @GetMapping("/download")
    public String download(String fileNo, HttpServletResponse response){
        return archiveService.downloadArchive(fileNo, response);
    }

}
