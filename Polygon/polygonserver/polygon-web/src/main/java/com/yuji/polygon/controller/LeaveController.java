package com.yuji.polygon.controller;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.service.LeaveService;
import com.yuji.polygon.service.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

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
    public String addLeaveFlow(@Valid Leave leave, String[] eNo,String[] eName){
        if (eNo.length < ConstantValue.LEAVE_AUDIT_LENGTH || eName.length < ConstantValue.LEAVE_AUDIT_LENGTH){
            throw new APIException(ResultCode.VALIDATE_FAILED.getCode(),"签审者不足");
        }
        return leaveSerice.addLeaveFlow(leave,eNo,eName);
    }

    @PutMapping("/update")
    public String updateLeaveFlow(@RequestBody @Valid Audit audit){
        return leaveSerice.updateLeaveFlow(audit);
    }

    @GetMapping("/page")
    public Page getLeavePage(Leave leave, int currentPageNumber, int pageSize){
        return leaveSerice.getLeavePage(leave,currentPageNumber,pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLeaveFlow(int id){
        return leaveSerice.deleteLeaveFlow(id);
    }

}
