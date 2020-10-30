package com.yuji.polygon.service.impl;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.LeaveMapper;
import com.yuji.polygon.service.LeaveService;
import com.yuji.polygon.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @className: LeaveSericeImpl
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:08:00
 */

@Service
public class LeaveSericeImpl implements LeaveService {

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    FlowServiceImpl flowService;

    @Autowired
    FlowLineServiceImpl flowLineService;


    @Override
    public ResultVO addLeaveFlow(Leave leave, Audit audit) {

        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("请假流程");
        flow.setFlowName("请假流程");
        Date date = new Date(System.currentTimeMillis());
        flow.setGmtCreate(date);
        flow.setGmtModified(date);
        ResultVO flowResult = flowService.insertFlow(flow);
        if (flowResult.getCode() != ResultCode.SUCCESS.getCode()){
            return flowResult;
        }

        //创建请假单
        leave.setFlowNo(flow.getFlowNo());
        //赋予当前流程节点
        leave.setCurrentNode(1);
        leave.setGmtCreate(date);
        leave.setGmtModified(date);
        if (leaveMapper.insertLeave(leave) < 1){
            return new ResultVO(ResultCode.FAILED,"创建请假单失败");
        }

        //创建流程线
        FlowLine flowLine = new FlowLine();
        flowLine.setFlowNo(flow.getFlowNo());
        flowLine.setNextNode(2);
        flowLine.setGmtCreate(date);
        flowLine.setGmtModified(date);
        ResultVO flowLineResult = flowLineService.insertFlowLine(flowLine);
        if (flowLineResult.getCode() != ResultCode.SUCCESS.getCode()){
            return flowLineResult;
        }

        return new ResultVO("提交成功");
    }

    @Override
    public ResultVO updateLeaveFlow(Leave leave) {
        return null;
    }

    @Override
    public ResultVO<Leave> findLeaveById(int id) {
        Leave leave = leaveMapper.findLeaveById(id);
        return new ResultVO<>(leave);
    }
}
