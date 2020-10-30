package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.LeaveMapper;
import com.yuji.polygon.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: LeaveSerice
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:08:00
 */

@Service
public class LeaveSerice {

    private final static int SUCCESS_CODE = 1000;

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    FlowService flowService;

    @Autowired
    FlowNodeService flowNodeService;

    @Autowired
    FlowLineService flowLineService;

    @Autowired
    AuditServie auditServie;


    public ResultVO addLeaveFlow(Leave leave) {
        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("请假流程");
        flow.setFlowName("请假流程");

        flow.setGmtCreate(CommonUtil.getNowTime());
        flow.setGmtModified(CommonUtil.getNowTime());
        ResultVO flowResult = flowService.insertFlow(flow);
        if (flowResult.getCode() != SUCCESS_CODE){
            return flowResult;
        }

        //创建流程节点
        FlowNode firstNode = new FlowNode();
        firstNode.setFlowNo(flow.getFlowNo());
        //应由流程角色表获取
        firstNode.setFlowNodeName("主任审批");
        firstNode.setEmployeeNo("X000");
        firstNode.setEmployeeName("X000");
        firstNode.setGmtCreate(CommonUtil.getNowTime());
        firstNode.setGmtModified(CommonUtil.getNowTime());
        ResultVO firstNodeResult = flowNodeService.insertFlowNode(firstNode);
        if (firstNodeResult.getCode() != SUCCESS_CODE){
            return firstNodeResult;
        }

        FlowNode secondNode = new FlowNode();
        secondNode.setFlowNo(flow.getFlowNo());
        //应由流程角色表获取
        secondNode.setFlowNodeName("部长审批");
        secondNode.setEmployeeNo("Z000");
        secondNode.setEmployeeName("Z000");
        secondNode.setGmtCreate(CommonUtil.getNowTime());
        secondNode.setGmtModified(CommonUtil.getNowTime());
        ResultVO secondNodeResult = flowNodeService.insertFlowNode(secondNode);
        if (secondNodeResult.getCode() != SUCCESS_CODE){
            return secondNodeResult;
        }

        //创建流程线
        FlowLine firstLine = new FlowLine();
        firstLine.setFlowNo(flow.getFlowNo());
        firstLine.setPreNode(0);
        firstLine.setNextNode(firstNode.getId());
        firstLine.setGmtCreate(CommonUtil.getNowTime());
        firstLine.setGmtModified(CommonUtil.getNowTime());
        ResultVO firstLineResult = flowLineService.insertFlowLine(firstLine);
        if (firstLineResult.getCode() != SUCCESS_CODE){
            return firstLineResult;
        }

        FlowLine secondLine = new FlowLine();
        secondLine.setFlowNo(flow.getFlowNo());
        secondLine.setPreNode(firstNode.getId());
        secondLine.setNextNode(secondNode.getId());
        secondLine.setGmtCreate(CommonUtil.getNowTime());
        secondLine.setGmtModified(CommonUtil.getNowTime());
        ResultVO secondLineResult = flowLineService.insertFlowLine(secondLine);
        if (secondLineResult.getCode() != SUCCESS_CODE){
            return secondLineResult;
        }

        //创建请假单
        leave.setFlowNo(flow.getFlowNo());
        //赋予当前流程节点
        leave.setCurrentNode(firstNode.getId());
        leave.setGmtCreate(CommonUtil.getNowTime());
        leave.setGmtModified(CommonUtil.getNowTime());
        if (leaveMapper.insertLeave(leave) < 1){
            return new ResultVO(ResultCode.FAILED,"创建请假单失败");
        }


        //为每个节点创建审批记录
        Audit firstAudit = new Audit();
        firstAudit.setBusinessNo(String.valueOf(leave.getId()));
        firstAudit.setEmployeeNo(firstNode.getEmployeeNo());
        firstAudit.setEmployeeName(firstNode.getEmployeeName());
        firstAudit.setFlowNodeNo(firstNode.getId());
        ResultVO firstAuditResult = auditServie.insertAudit(firstAudit);
        if (firstAuditResult.getCode() != SUCCESS_CODE){
            return firstAuditResult;
        }

        Audit secondAudit = new Audit();
        secondAudit.setBusinessNo(String.valueOf(leave.getId()));
        secondAudit.setEmployeeNo(secondNode.getEmployeeNo());
        secondAudit.setEmployeeName(secondNode.getEmployeeName());
        secondAudit.setFlowNodeNo(secondNode.getId());
        ResultVO secondAuditResult = auditServie.insertAudit(secondAudit);
        if (secondAuditResult.getCode() != SUCCESS_CODE){
            return secondAuditResult;
        }
        return new ResultVO("提交成功");
    }


    public ResultVO updateLeaveFlow(Leave leave) {
        return null;
    }


    public ResultVO<Leave> findLeaveById(int id) {
        Leave leave = leaveMapper.findLeaveById(id);
        return new ResultVO<>(leave);
    }
}
