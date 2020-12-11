package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.LeaveMapper;

import com.yuji.polygon.utils.CommonUtil;
import com.yuji.polygon.utils.ConstantValue;
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

    @Autowired
    EmployeeService employeeService;

    /**
     * Spring框架的事务管理默认地只在发生不受控异常（RuntimeException和Error）时才进行事务回滚
     * @param leave
     * @param eNo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int addLeaveFlow(Leave leave, String[] eNo) {

        List<Employee> employees = employeeService.getEmployeeByNos(eNo);

        if (employees.size() != ConstantValue.LEAVE_AUDIT_LENGTH){
            return 0;
        }

        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("请假流程");

        flow.setGmtCreate(CommonUtil.getNowTime());
        flow.setGmtModified(CommonUtil.getNowTime());
        flowService.insertFlow(flow);


        //创建流程节点
        FlowNode firstNode = new FlowNode();
        firstNode.setEmployeeNo(employees.get(0).getNo());
        firstNode.setEmployeeName(employees.get(0).getName());
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName(ConstantValue.LEAVE_ONE_AUDIT);
        firstNode.setGmtCreate(CommonUtil.getNowTime());
        firstNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(firstNode);

        FlowNode secondNode = new FlowNode();
        secondNode.setEmployeeNo(employees.get(1).getNo());
        secondNode.setEmployeeName(employees.get(1).getName());
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

        return 1;
    }


    @Transactional(rollbackFor = Exception.class)
    public int updateLeaveFlow(Audit audit) {

        audit.setAuditDate(new Date());
        auditServie.updateAudit(audit);

        //获取请假单
        Leave leave = leaveMapper.getLeaveById(audit.getBusinessNo());
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


        return 1;
    }


    public Leave getLeaveById(int id) {
        return leaveMapper.getLeaveById(id);
    }

    public Page<Leave> getLeavePage(Leave leave, int currentPageNumber, int pageSize) {
        int startIndex = (currentPageNumber - 1) * pageSize;
        int totalCount = leaveMapper.countTotal(leave);
        Page page = new Page(currentPageNumber, totalCount);

        List<Leave> records = leaveMapper.listLeave(leave,startIndex,pageSize);
        page.setRecords(records);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteLeaveFlow(int id) {
        String flowNo = leaveMapper.getLeaveById(id).getFlowNo();
        leaveMapper.deleteLeaveById(id);
        flowService.deleteFlowByFlowNo(flowNo);
        flowNodeService.deleteFlowNodeByFlowNo(flowNo);
        flowLineService.deleteFlowLineByFlowNo(flowNo);
        auditServie.deleteAuditByBusinessNo(id);

        return 1;
    }

}
