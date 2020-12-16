package com.yuji.polygon.service;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.mapper.FileSignMapper;

import com.yuji.polygon.utils.CommonUtil;
import com.yuji.polygon.utils.ConstantValue;
import com.yuji.polygon.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @className: FileSignService
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-02 18:47:00
 */

@Service
public class FileSignService {

    @Value("${custom.path}")
    String customPath;

    @Autowired
    FileSignMapper fileSignMapper;

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

    @Transactional(rollbackFor = Exception.class)
    public int addFlowSignFlow(FileSign fileSign,String[] eNo, MultipartFile file) {

        List<Employee> employees = employeeService.getEmployeeByNos(eNo);
        if (employees.size() != ConstantValue.LEAVE_AUDIT_LENGTH){
            return 0;
        }
        //创建流程
        Flow flow = new Flow();
        flow.setFlowNo(CommonUtil.randomUid(12));
        flow.setFlowName("项目文件签审流程");

        flow.setGmtCreate(CommonUtil.getNowTime());
        flow.setGmtModified(CommonUtil.getNowTime());
        flowService.insertFlow(flow);


        //创建流程节点
        FlowNode firstNode = new FlowNode();
        firstNode.setApproveNo(employees.get(0).getNo());
        firstNode.setApproveName(employees.get(0).getName());
        firstNode.setFlowNo(flow.getFlowNo());
        firstNode.setFlowNodeName(ConstantValue.FILE_SIGN_ONE_AUDIT);
        firstNode.setGmtCreate(CommonUtil.getNowTime());
        firstNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(firstNode);

        FlowNode secondNode = new FlowNode();
        secondNode.setApproveNo(employees.get(1).getNo());
        secondNode.setApproveName(employees.get(1).getName());
        secondNode.setFlowNo(flow.getFlowNo());
        secondNode.setFlowNodeName(ConstantValue.FILE_SING_THREE_AUDIT);
        secondNode.setGmtCreate(CommonUtil.getNowTime());
        secondNode.setGmtModified(CommonUtil.getNowTime());
        flowNodeService.insertFlowNode(secondNode);

        FlowNode thirdNode = new FlowNode();
        thirdNode.setApproveNo(employees.get(2).getNo());
        thirdNode.setApproveName(employees.get(2).getName());
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
        //保存文件
        String originalFilename = file.getOriginalFilename();
        String savePath = customPath + "/" + flow.getFlowNo() + "/" + originalFilename.substring(0, originalFilename.lastIndexOf("."));
        savePath = FileUtil.getInstance().save(savePath, file);
        fileSign.setFilePath(savePath);
        fileSignMapper.insertFileSign(fileSign);


        //为每个节点创建审批记录
        Approve firstApprove = new Approve();
        firstApprove.setBusinessNo(fileSign.getId());
        firstApprove.setApproveNo(firstNode.getApproveNo());
        firstApprove.setApproveName(firstNode.getApproveName());
        firstApprove.setFlowNodeNo(firstNode.getId());
        approveService.insertApprove(firstApprove);


        Approve secondApprove = new Approve();
        secondApprove.setBusinessNo(fileSign.getId());
        secondApprove.setApproveNo(secondNode.getApproveNo());
        secondApprove.setApproveName(secondNode.getApproveName());
        secondApprove.setFlowNodeNo(secondNode.getId());
        approveService.insertApprove(secondApprove);

        Approve thirdApprove = new Approve();
        thirdApprove.setBusinessNo(fileSign.getId());
        thirdApprove.setApproveNo(thirdNode.getApproveNo());
        thirdApprove.setApproveName(thirdNode.getApproveName());
        thirdApprove.setFlowNodeNo(thirdNode.getId());
        approveService.insertApprove(thirdApprove);

        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateFileSignFlow(Approve approve) {
        approve.setApproveDate(CommonUtil.getNowTime());
        approveService.updateApprove(approve);

        //获取请假单
        FileSign fileSign = fileSignMapper.getFileSignById(approve.getBusinessNo());
        //获取流程线
        FlowLine flowLine = flowLineService.findFlowLineByPreNode(fileSign.getCurrentNode());

        if (flowLine == null || approve.getApproveState() == -1) {
            //已到流程的终点，设置节点为0，流程结束
            fileSign.setCurrentNode(0);
            fileSign.setFlowState(1);
        } else {
            //设置为下一个节点
            fileSign.setCurrentNode(flowLine.getNextNode());
        }
        fileSignMapper.updateFileSign(fileSign);


        return 1;
    }

    public FileSign getFileSignById(int id) {
        return fileSignMapper.getFileSignById(id);
    }

    public Page<FileSign> getFileSignPage(FileSign fileSign, int pageNum, int pageSize) {
        int startIndex = (pageNum - 1) * pageSize;
        int totalCount = fileSignMapper.countTotal(fileSign);
        Page page = new Page(pageNum, totalCount);

        List<FileSign> records = fileSignMapper.listFileSign(fileSign,startIndex,pageSize);
        page.setRecords(records);
        return page;
    }

    public void downloadSignFile(int id, HttpServletResponse response) {
        Optional<FileSign> optional = Optional.ofNullable(fileSignMapper.getFileSignById(id));
        if (!optional.isPresent()) {
            throw new APIException("文档不存在");
        }
        FileUtil.getInstance().download(optional.get().getFilePath(), response);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteFileSignById(int id) {
        String flowNo = fileSignMapper.getFileSignById(id).getFlowNo();
        fileSignMapper.deleteFileSignById(id);
        flowService.deleteFlowByFlowNo(flowNo);
        flowNodeService.deleteFlowNodeByFlowNo(flowNo);
        flowLineService.deleteFlowLineByFlowNo(flowNo);
        approveService.deleteApproveByBusinessNo(id);
        return 1;
    }
}
