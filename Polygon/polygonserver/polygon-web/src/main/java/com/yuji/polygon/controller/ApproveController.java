package com.yuji.polygon.controller;

import com.yuji.polygon.entity.ApproveVO;
import com.yuji.polygon.service.ApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: ApproveController
 * @description: TODO
 * @author: yuji
 * @create: 2020-12-22 13:30:48
 */

@RestController
@RequestMapping("/approve")
public class ApproveController {

    @Autowired
    ApproveService approveService;

    @GetMapping("/list/{flowNo}")
    public List<ApproveVO> getApproveByFlowNo(@PathVariable("flowNo") String flowNo){
        return approveService.getApproveByFlowNo(flowNo);
    }
}
