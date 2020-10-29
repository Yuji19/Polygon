package com.yuji.polygon;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.LeaveMapper;
import com.yuji.polygon.service.impl.AuditServieImpl;
import com.yuji.polygon.service.impl.FlowLineServiceImpl;
import com.yuji.polygon.service.impl.FlowNodeServiceImpl;
import com.yuji.polygon.service.impl.FlowServiceImpl;
import com.yuji.polygon.util.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@SpringBootTest
class PolygonApplicationTests {

    @Test
    void contextLoads() {
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < 10; i++){
            int number;
            if (i == 0 || i == 1){
                number = random.nextInt(25);
                stringBuffer.append(str.charAt(number));
            }else{
                number = random.nextInt(9);
                stringBuffer.append(number);
            }


        }
        String uid = stringBuffer.toString();
        System.out.println(uid);

    }


    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    FlowServiceImpl flowService;

    @Autowired
    FlowLineServiceImpl flowLineService;

    @Autowired
    FlowNodeServiceImpl flowNodeService;

    @Autowired
    AuditServieImpl auditServie;

    @Test
    void addLeaveFlow(){
        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("请假流程");
        flow.setFlowName("请假流程");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            date = sdf.parse(sdf.format(date));
        }catch (ParseException e) {
            e.printStackTrace();
        }

        flow.setGmtCreate(date);
        flow.setGmtModified(date);
        ResultVO flowResult = flowService.insertFlow(flow);
        System.out.println(flowResult.getData());

        //创建流程节点
        FlowNode firstNode = new FlowNode();
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName("主任审批");
        firstNode.setEmployeeNo("X000");
        firstNode.setEmployeeName("X000");
        flowNodeService.insertFlowNode(firstNode);

        FlowNode secondNode = new FlowNode();
        secondNode.setFlowNo(flow.getFlowNo());
        secondNode.setFlowNodeName("部长审批");
        secondNode.setEmployeeNo("Z000");
        secondNode.setEmployeeName("Z000");
        flowNodeService.insertFlowNode(secondNode);

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

        //创建流程线
        FlowLine flowLine = new FlowLine();
        flowLine.setFlowNo(flow.getFlowNo());
        flowLine.setCurrentNode(1);
        flowLine.setNextNode(2);
        //0:未审批 1:已审批
        flowLine.setFlowState(0);
        flowLine.setGmtCreate(date);
        flowLine.setGmtModified(date);
        ResultVO flowLineResult = flowLineService.insertFlowLine(flowLine);
        System.out.println(flowLineResult.getData());

        //为每个节点创建审批记录
        Audit firstAudit = new Audit();
        firstAudit.setBusinessNo(String.valueOf(leave.getId()));
        firstAudit.setEmployeeNo(firstNode.getEmployeeNo());
        firstAudit.setEmployeeName(firstNode.getEmployeeName());
        firstAudit.setFlowNodeNo(firstNode.getId());
        auditServie.insertAudit(firstAudit);

        Audit secondAudit = new Audit();
        secondAudit.setBusinessNo(String.valueOf(leave.getId()));
        secondAudit.setEmployeeNo(secondNode.getEmployeeNo());
        secondAudit.setEmployeeName(secondNode.getEmployeeName());
        secondAudit.setFlowNodeNo(secondNode.getId());
        auditServie.insertAudit(secondAudit);

    }

}
