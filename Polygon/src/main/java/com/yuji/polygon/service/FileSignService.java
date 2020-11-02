package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.FileSignMapper;
import com.yuji.polygon.util.CommonUtil;
import com.yuji.polygon.util.ConstantValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: FileSignService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-02 18:47:00
 */

@Service
public class FileSignService {

    @Autowired
    FileSignMapper fileSignMapper;

    @Autowired
    FlowService flowService;

    @Autowired
    FlowNodeService flowNodeService;

    @Autowired
    FlowLineService flowLineService;

    @Autowired
    AuditServie auditServie;

    @Transactional
    public String addFlowSignFlow(FileSign fileSign, FlowNode firstNode, FlowNode secondNode, FlowNode thirdNode){
        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("项目文件签审流程");

        flow.setGmtCreate(CommonUtil.getNowTime());
        flow.setGmtModified(CommonUtil.getNowTime());
        flowService.insertFlow(flow);


        //创建流程节点
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName(ConstantValue.FILE_SIGN_ONE_AUDIT);
        firstNode.setGmtCreate(CommonUtil.getNowTime());
        firstNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(firstNode);


        secondNode.setFlowNo(flow.getFlowNo());
        secondNode.setFlowNodeName(ConstantValue.FILE_SING_THREE_AUDIT);
        secondNode.setGmtCreate(CommonUtil.getNowTime());
        secondNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(secondNode);

        thirdNode.setFlowNo(flow.getFlowNo());
        thirdNode.setFlowNodeName(ConstantValue.FILE_SING_TWO_AUDIT);
        thirdNode.setGmtCreate(CommonUtil.getNowTime());
        thirdNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(thirdNode);


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
        thirdLine.setNextNode(thirdNode.getId());
        thirdLine.setGmtCreate(CommonUtil.getNowTime());
        thirdLine.setGmtModified(CommonUtil.getNowTime());
        flowLineService.insertFlowLine(thirdLine);

        //创建项目文件签审单
        fileSign.setFlowNo(flow.getFlowNo());
        //赋予当前流程节点
        fileSign.setCurrentNode(firstNode.getId());
        fileSign.setGmtCreate(CommonUtil.getNowTime());
        fileSign.setGmtModified(CommonUtil.getNowTime());
        fileSignMapper.insertFileSign(fileSign);


        //为每个节点创建审批记录
        Audit firstAudit = new Audit();
        firstAudit.setBusinessNo(fileSign.getId());
        firstAudit.setEmployeeNo(firstNode.getEmployeeNo());
        firstAudit.setEmployeeName(firstNode.getEmployeeName());
        firstAudit.setFlowNodeNo(firstNode.getId());
        auditServie.insertAudit(firstAudit);


        Audit secondAudit = new Audit();
        secondAudit.setBusinessNo(fileSign.getId());
        secondAudit.setEmployeeNo(secondNode.getEmployeeNo());
        secondAudit.setEmployeeName(secondNode.getEmployeeName());
        secondAudit.setFlowNodeNo(secondNode.getId());
        auditServie.insertAudit(secondAudit);

        Audit thirdAudit = new Audit();
        thirdAudit.setBusinessNo(fileSign.getId());
        thirdAudit.setEmployeeNo(thirdNode.getEmployeeNo());
        thirdAudit.setEmployeeName(thirdNode.getEmployeeName());
        thirdAudit.setFlowNodeNo(thirdNode.getId());
        auditServie.insertAudit(thirdAudit);

        return "提交成功";
    }

    @Transactional
    public String updateFileSignFlow(Audit audit){
        audit.setAuditDate(CommonUtil.getNowTime());
        auditServie.updateAudit(audit);

        //获取请假单
        FileSign fileSign = fileSignMapper.findFileSignById(audit.getBusinessNo());
        //获取流程线
        FlowLine flowLine = flowLineService.findFlowLineByPreNode(fileSign.getCurrentNode()).getData();

        if (flowLine == null || audit.getAuditState() == -1){
            //已到流程的终点，设置节点为0，流程结束
            fileSign.setCurrentNode(0);
            fileSign.setFlowState(1);
        }else{
            //设置为下一个节点
            fileSign.setCurrentNode(flowLine.getNextNode());
        }
        fileSignMapper.updateFileSign(fileSign);


        return "审批成功";
    }
}
