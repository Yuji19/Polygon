package com.yuji.polygon.controller;

import com.yuji.polygon.entity.Audit;
import com.yuji.polygon.entity.FlowNode;
import com.yuji.polygon.entity.Leave;
import com.yuji.polygon.entity.Page;
import com.yuji.polygon.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @className: LeaveController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-03 15:30:00
 */

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    LeaveService leaveSerice;

    @PostMapping("/add")
    public String addLeaveFlow(@Valid Leave leave, @Valid FlowNode firstNode, @Valid FlowNode secondNode){
        return leaveSerice.addLeaveFlow(leave,firstNode,secondNode);
    }

    @PostMapping("/update")
    public String updateLeaveFlow(@RequestBody @Valid Audit audit){
        return leaveSerice.updateLeaveFlow(audit);
    }

    @PostMapping("/page")
    public Page getLeavePage(Leave leave, int currentPageNumber, int pageSize){
        return leaveSerice.getLeavePage(leave,currentPageNumber,pageSize);
    }

    @GetMapping("/delete")
    public String deleteLeaveFlow(int id){
        return leaveSerice.deleteLeaveFlow(id);
    }

}
