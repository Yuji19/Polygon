package com.yuji.polygon;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.LeaveMapper;
import com.yuji.polygon.service.AuditServie;
import com.yuji.polygon.service.FlowLineService;
import com.yuji.polygon.service.FlowNodeService;
import com.yuji.polygon.service.FlowService;
import com.yuji.polygon.util.CommonUtil;
import com.yuji.polygon.util.ConstantValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@SpringBootTest
class PolygonApplicationTests {

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    FlowService flowService;

    @Autowired
    FlowLineService flowLineService;

    @Autowired
    FlowNodeService flowNodeService;

    @Autowired
    AuditServie auditServie;

    @Test
    void contextLoads() {

        Audit audit = auditServie.findAuditByEmpolyeeNo("X000").getData();
        auditServie.updateAudit(audit);

    }




    @Test
    @Transactional
    void addLeaveFlow(){
        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("请假流程");
        Date date = CommonUtil.getNowTime();

        flow.setGmtCreate(date);
        flow.setGmtModified(date);

        ResultVO flowResult = flowService.insertFlow(flow);
        System.out.println(flowResult.getData());

        //创建流程节点
        FlowNode firstNode = new FlowNode();
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName(ConstantValue.LEAVE_ONE_AUDIT);
        firstNode.setEmployeeNo("X000");
        firstNode.setEmployeeName("X000");
        firstNode.setGmtCreate(date);
        firstNode.setGmtModified(date);
        flowNodeService.insertFlowNode(firstNode);

        FlowNode secondNode = new FlowNode();
        secondNode.setFlowNo(flow.getFlowNo());
        secondNode.setFlowNodeName(ConstantValue.LEAVE_TWO_AUDIT);
        secondNode.setEmployeeNo("Z000");
        secondNode.setEmployeeName("Z000");
        secondNode.setGmtCreate(date);
        secondNode.setGmtModified(date);
        flowNodeService.insertFlowNode(secondNode);

        //创建流程线
        FlowLine firstLine = new FlowLine();
        firstLine.setFlowNo(flow.getFlowNo());
        firstLine.setPreNode(0);
        firstLine.setNextNode(firstNode.getId());
        firstLine.setGmtCreate(date);
        firstLine.setGmtModified(date);
        ResultVO firstLineResult = flowLineService.insertFlowLine(firstLine);
        System.out.println(firstLineResult.getData());

        FlowLine secondLine = new FlowLine();
        secondLine.setFlowNo(flow.getFlowNo());
        secondLine.setPreNode(firstNode.getId());
        secondLine.setNextNode(secondNode.getId());
        secondLine.setGmtCreate(date);
        secondLine.setGmtModified(date);
        ResultVO secondLineResult = flowLineService.insertFlowLine(secondLine);
        System.out.println(secondLineResult.getData());

        //创建请假单
        Leave leave = new Leave();
        leave.setEmployeeNo("Y000");
        leave.setEmployeeName("Y000");
        leave.setLeaveType("病假");
        leave.setLeaveReason("感冒");
        leave.setStartDate(date);
        leave.setEndDate(date);
        leave.setFlowNo(flow.getFlowNo());
        //赋予当前流程节点
        leave.setCurrentNode(firstNode.getId());
        leave.setGmtCreate(date);
        leave.setGmtModified(date);
        if (leaveMapper.insertLeave(leave) < 1){
            System.out.println("创建请假单失败");
        }


        //为每个节点创建审批记录
        Audit firstAudit = new Audit();
        firstAudit.setBusinessNo(leave.getId());
        firstAudit.setEmployeeNo(firstNode.getEmployeeNo());
        firstAudit.setEmployeeName(firstNode.getEmployeeName());
        firstAudit.setFlowNodeNo(firstNode.getId());
        ResultVO firstAuditResult = auditServie.insertAudit(firstAudit);
        System.out.println(firstAuditResult.getData());

        Audit secondAudit = new Audit();
        secondAudit.setBusinessNo(leave.getId());
        secondAudit.setEmployeeNo(secondNode.getEmployeeNo());
        secondAudit.setEmployeeName(secondNode.getEmployeeName());
        secondAudit.setFlowNodeNo(secondNode.getId());
        ResultVO secondAuditResult = auditServie.insertAudit(secondAudit);
        System.out.println(secondAuditResult.getData());
        throw new APIException("事务回滚");
    }

    @Test
    public void LeaveFlowAgree(){

        //通过审批人的员工编号获取所有该员工的待审批流程
        //审批人选择某条审批，前端发送审批实例

        //先模拟成功通过的流程
        //更新审批表
        Audit audit = auditServie.findAuditByEmpolyeeNo("X000").getData();
        System.out.println(audit);
        audit.setAuditInfo("同意");
        audit.setAuditState(1);
        audit.setAuditDate(new Date());

        //获取请假单
        Leave leave = leaveMapper.findLeaveById(audit.getBusinessNo());
        //获取流程线
        FlowLine flowLine = flowLineService.findFlowLineByPreNode(leave.getCurrentNode()).getData();
        //根据更新结果，更新请假单
        if (auditServie.updateAudit(audit).getCode() == ConstantValue.SUCCESS_CODE){
            if (flowLine == null || audit.getAuditState() == -1){
                //已到流程的终点，设置节点为0，流程结束
                leave.setCurrentNode(0);
                leave.setFlowState(1);
            }else{
                //设置为下一个节点
                leave.setCurrentNode(flowLine.getNextNode());
            }
            leaveMapper.updateLeave(leave);

        }else{
            System.out.println("审批出错");
        }

    }

    @Test
    public void LeaveFlowAgainst(){

        Audit audit = auditServie.findAuditByEmpolyeeNo("Z000").getData();
        audit.setAuditInfo("ok");
        audit.setAuditState(1);
        audit.setAuditDate(new Date());

        //获取请假单
        Leave leave = leaveMapper.findLeaveById(audit.getBusinessNo());
        //获取流程线
        FlowLine flowLine = flowLineService.findFlowLineByPreNode(leave.getCurrentNode()).getData();
        //根据更新结果，更新请假单
        if (auditServie.updateAudit(audit).getCode() == ConstantValue.SUCCESS_CODE){
            if (flowLine == null || audit.getAuditState() == -1){
                //已到流程的终点，设置节点为0，流程结束
                leave.setCurrentNode(0);
                leave.setFlowState(1);
            }else{
                //设置为下一个节点
                leave.setCurrentNode(flowLine.getNextNode());
            }

        }
    }

}
