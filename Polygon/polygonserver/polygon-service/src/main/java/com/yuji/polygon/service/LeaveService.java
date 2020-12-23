package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.LeaveMapper;

import com.yuji.polygon.utils.CommonUtil;
import com.yuji.polygon.utils.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    ApproveService approveService;

    @Autowired
    EmployeeService employeeService;

    /**
     * Spring框架的事务管理默认地只在发生不受控异常（RuntimeException和Error）时才进行事务回滚
     * 创建流程
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
        firstNode.setApproveNo(employees.get(0).getNo());
        firstNode.setApproveName(employees.get(0).getName());
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName(ConstantValue.LEAVE_ONE_AUDIT);
        firstNode.setGmtCreate(CommonUtil.getNowTime());
        firstNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(firstNode);

        FlowNode secondNode = new FlowNode();
        secondNode.setApproveNo(employees.get(1).getNo());
        secondNode.setApproveName(employees.get(1).getName());
        secondNode.setFlowNo(flow.getFlowNo());
        secondNode.setFlowNodeName(ConstantValue.LEAVE_TWO_AUDIT);
        secondNode.setGmtCreate(CommonUtil.getNowTime());
        secondNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(secondNode);

        FlowNode endNode = new FlowNode();
        endNode.setApproveNo(leave.getEmployeeNo());
        endNode.setApproveName(leave.getEmployeeName());
        endNode.setFlowNo(flow.getFlowNo());
        endNode.setFlowNodeName(ConstantValue.END_NODE);
        endNode.setGmtCreate(new Date());
        endNode.setGmtModified(new Date());
        flowNodeService.insertFlowNode(endNode);


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

        FlowLine thirdLine = new FlowLine();
        thirdLine.setFlowNo(flow.getFlowNo());
        thirdLine.setPreNode(secondNode.getId());
        thirdLine.setNextNode(endNode.getId());
        thirdLine.setGmtCreate(new Date());
        thirdLine.setGmtModified(new Date());
        flowLineService.insertFlowLine(thirdLine);


        //创建请假单
        leave.setFlowNo(flow.getFlowNo());
        //赋予当前流程节点
        leave.setCurrentNode(firstNode.getId());
        leave.setGmtCreate(CommonUtil.getNowTime());
        leave.setGmtModified(CommonUtil.getNowTime());
        leaveMapper.insertLeave(leave);


        //为每个节点创建审批记录
        Approve firstApprove = new Approve();
        firstApprove.setFlowNo(flow.getFlowNo());
        firstApprove.setBusinessNo(leave.getId());
        firstApprove.setApproveNo(firstNode.getApproveNo());
        firstApprove.setApproveName(firstNode.getApproveName());
        firstApprove.setApproveState(0);
        firstApprove.setFlowNodeNo(firstNode.getId());
        approveService.insertApprove(firstApprove);


        Approve secondApprove = new Approve();
        secondApprove.setFlowNo(flow.getFlowNo());
        secondApprove.setBusinessNo(leave.getId());
        secondApprove.setApproveNo(secondNode.getApproveNo());
        secondApprove.setApproveName(secondNode.getApproveName());
        secondApprove.setApproveState(0);
        secondApprove.setFlowNodeNo(secondNode.getId());
        approveService.insertApprove(secondApprove);

        return 1;
    }

    /**
     * 审批，流程更新
     * @param approve
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateLeaveFlow(Approve approve) {

        approve.setApproveDate(new Date());
        approveService.updateApprove(approve);

        //获取请假单
        Leave leave = leaveMapper.getLeaveById(approve.getBusinessNo());
        //获取流程线
        FlowLine flowLine = flowLineService.findFlowLineByPreNode(leave.getCurrentNode());
        if (flowLine == null){
            throw new APIException("流程节点缺失");
        }
        //获取终点节点
        FlowNode endNode = flowNodeService.getFlowNodeByFlowNoAndFlowNodeName(leave.getFlowNo(),ConstantValue.END_NODE);
        if (endNode == null){
            throw new APIException("流程终点缺失");
        }

        //驳回
        if (approve.getApproveState() == -1) {
            //已到流程的终点，流程结束
            leave.setCurrentNode(endNode.getId());
            leave.setFlowState(1);
        } else {
            //下一个节点为流程终点
            if (flowLine.getNextNode() == endNode.getId()){
                leave.setFlowState(1);
            }
            //设置为下一个节点
            leave.setCurrentNode(flowLine.getNextNode());
        }
        leaveMapper.updateLeave(leave);


        return 1;
    }


    public Leave getLeaveById(int id) {
        return leaveMapper.getLeaveById(id);
    }

    /**
     * 待审批列表
     * @param flowNo
     * @param approveNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<FlowVO> getLeaveFlowPage(String flowNo, String approveNo, int pageNum, int pageSize){
        int startIndex = (pageNum - 1) * pageSize;
        int totalCount = leaveMapper.countTotalLeaveFlow(flowNo,approveNo);
        Page page = new Page(pageNum, totalCount);
        List<FlowVO> records = leaveMapper.getLeaveFlowPage(flowNo,approveNo,startIndex,pageSize);
        page.setRecords(records);
        return page;
    }

    /**
     * 我的申请列表
     * @param flowNo
     * @param employeeNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<FlowVO> getMineLeaveFlowPage(String flowNo, String employeeNo, int pageNum, int pageSize){
        int startIndex = (pageNum - 1) * pageSize;
        int totalCount = leaveMapper.countTotalMineLeaveFlow(flowNo,employeeNo);
        Page page = new Page(pageNum, totalCount);
        List<FlowVO> records = leaveMapper.getMineLeaveFlowPage(flowNo,employeeNo,startIndex,pageSize);
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
        approveService.deleteApproveByBusinessNo(id);

        return 1;
    }

}
