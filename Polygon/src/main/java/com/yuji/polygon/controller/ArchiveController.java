package com.yuji.polygon.controller;


import com.yuji.polygon.entity.APIException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ArchiveController
 * @Description TODO
 * @Author yuji
 * @Date 2020-08-04 17:25
 * @Version 1.0
 */

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @GetMapping("/all")
    public String listArchives(Integer currentPageNum, Integer pageSize){
        //Archive archive = new Archive();
        //archive.setFileName("ceshi");
        //return archive;
        throw new APIException("测试抛出异常");
    }
}
