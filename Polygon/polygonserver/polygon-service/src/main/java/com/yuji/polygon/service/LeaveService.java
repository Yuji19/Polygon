package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.LeaveMapper;

import com.yuji.polygon.service.utils.CommonUtil;
import com.yuji.polygon.service.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: LeaveSerice
 * @description: TODO
 * @author: yuji
 * @create: 2020-10-28 19:08:00
 */

@Service
public class LeaveService {

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


    @Transactional
    public String addLeaveFlow(Leave leave, FlowNode firstNode, FlowNode secondNode) {
        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("请假流程");

        flow.setGmtCreate(CommonUtil.getNowTime());
        flow.setGmtModified(CommonUtil.getNowTime());
        flowService.insertFlow(flow);


        //创建流程节点
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName(ConstantValue.LEAVE_ONE_AUDIT);
        firstNode.setGmtCreate(CommonUtil.getNowTime());
        firstNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(firstNode);


        secondNode.setFlowNo(flow.getFlowNo());
        secondNode.setFlowNodeName(ConstantValue.LEAVE_TWO_AUDIT);
        secondNode.setGmtCreate(CommonUtil.getNowTime());
        secondNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(secondNode);


        //创建流程线
        FlowLine firstLine = new FlowLine();
        firstLine.setFlowNo(flow.getFlowNo());
        firstLine.setPreNode(0);
        firstLine.setNextNode(firstNode.getId());
        firstLine.setGmtCreate(CommonUtil.getNowTime());
        firstLine.setGmtModified(CommonUtil.getNowTime());
        flowLineService.insertFlowLine(firstLine);


        FlowLine secondLine = new FlowLine();
        secondLine.setFlowNo(flow.getFlowNo());
        secondLine.setPreNode(firstNode.getId());
        secondLine.setNextNode(secondNode.getId());
        secondLine.setGmtCreate(CommonUtil.getNowTime());
        secondLine.setGmtModified(CommonUtil.getNowTime());
        flowLineService.insertFlowLine(secondLine);


        //创建请假单
        leave.setFlowNo(flow.getFlowNo());
        //赋予当前流程节点
        leave.setCurrentNode(firstNode.getId());
        leave.setGmtCreate(CommonUtil.getNowTime());
        leave.setGmtModified(CommonUtil.getNowTime());
        leaveMapper.insertLeave(leave);


        //为每个节点创建审批记录
        Audit firstAudit = new Audit();
        firstAudit.setBusinessNo(leave.getId());
        firstAudit.setEmployeeNo(firstNode.getEmployeeNo());
        firstAudit.setEmployeeName(firstNode.getEmployeeName());
        firstAudit.setFlowNodeNo(firstNode.getId());
        auditServie.insertAudit(firstAudit);


        Audit secondAudit = new Audit();
        secondAudit.setBusinessNo(leave.getId());
        secondAudit.setEmployeeNo(secondNode.getEmployeeNo());
        secondAudit.setEmployeeName(secondNode.getEmployeeName());
        secondAudit.setFlowNodeNo(secondNode.getId());
        auditServie.insertAudit(secondAudit);

        return "提交成功";
    }


    @Transactional
    public String updateLeaveFlow(Audit audit) {

        audit.setAuditDate(new Date());
        auditServie.updateAudit(audit);

        //获取请假单
        Leave leave = leaveMapper.findLeaveById(audit.getBusinessNo());
        //获取流程线
        FlowLine flowLine = flowLineService.findFlowLineByPreNode(leave.getCurrentNode());

        if (flowLine == null || audit.getAuditState() == -1) {
            //已到流程的终点，设置节点为0，流程结束
            leave.setCurrentNode(0);
            leave.setFlowState(1);
        } else {
            //设置为下一个节点
            leave.setCurrentNode(flowLine.getNextNode());
        }
        leaveMapper.updateLeave(leave);


        return "审批成功";
    }


    public Leave findLeaveById(int id) {
        return leaveMapper.findLeaveById(id);
    }

    @Transactional
    public Page<Leave> getLeavePage(Leave leave, int currentPageNumber, int pageSize) {
        int startIndex = (currentPageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<>(4);
        map.put("leave", leave);
        map.put("startIndex", startIndex);
        map.put("pageSize", pageSize);
        int totalCount = leaveMapper.countTotal(leave);
        Page page = new Page(currentPageNumber, totalCount);

        List<Leave> records = leaveMapper.listLeave(map);
        page.setRecords(records);
        return page;
    }

    @Transactional
    public String deleteLeaveFlow(int id) {
        String flowNo = leaveMapper.findLeaveById(id).getFlowNo();
        leaveMapper.deleteLeaveById(id);
        flowService.deleteFlowByFlowNo(flowNo);
        flowNodeService.deleteFlowNodeByFlowNo(flowNo);
        flowLineService.deleteFlowLineByFlowNo(flowNo);
        auditServie.deleteAuditByBusinessNo(id);

        return "删除成功";
    }

}
