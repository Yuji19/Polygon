package com.yuji.polygon.controller;/**
 * Created by Enzo Cotter on 2020/8/4.
 */

import com.yuji.polygon.entity.APIException;
import com.yuji.polygon.entity.Anounce;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: FileSystem
 * @description:
 * @author: yuji
 * @create: 2020-08-04 21:50
 **/
@RestController
@RequestMapping("/anounce")
public class AnounceController {

    @GetMapping("/all")
    public String listAnounces(Integer currentPageNum, Integer pageSize){
        //Anounce anounce = new Anounce();
        //anounce.setFileName("ceshi");
        //return anounce;
        throw new APIException("测试抛出异常");
    }
}
