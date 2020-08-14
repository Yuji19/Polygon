package com.yuji.polygon.controller;


import com.yuji.polygon.entity.APIException;
import com.yuji.polygon.entity.Archive;
import com.yuji.polygon.service.impl.ArchiveServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

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
    public String insert(@NotNull Archive archive,@NotNull MultipartFile file){
        return archiveService.insertArchive(archive, file);
    }
}
