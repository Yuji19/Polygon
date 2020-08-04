package com.yuji.polygon.controller;/**
 * Created by Enzo Cotter on 2020/8/4.
 */

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
    public void listAnounces(Integer currentPageNum, Integer pageSize){

    }
}
