package com.yuji.polygon.advice;

import com.yuji.polygon.entity.*;
import com.yuji.polygon.service.EmployeeService;
import com.yuji.polygon.service.FlowNodeService;
import com.yuji.polygon.service.LeaveService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: MailAspect
 * @description: TODO
 * @author: yuji
 * @create: 2020-12-28 15:08:38
 */

@Aspect
@Component
public class MailAspect {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    FlowNodeService flowNodeService;

    @Autowired
    LeaveService leaveService;

    @Autowired
    EmployeeService employeeService;

    @Pointcut("execution(public * com.yuji.polygon.service.LeaveService.*(..))")
    public void serviceMail(){}

    @AfterReturning(pointcut = "serviceMail()",returning = "result")
    public void mailAfterService(JoinPoint joinPoint, Object result){
        //判断方法
        String methodName = joinPoint.getSignature().getName();
        if ("addLeaveFlow".equals(methodName)){
            int flowNodeNo = 0;
            Leave leave = null;
            //获取参数值
            Object[] paramValues = joinPoint.getArgs();
            for (int i = 0; i < paramValues.length; i++){
                if (paramValues[i] instanceof Leave){
                    leave = (Leave) paramValues[i];
                    flowNodeNo = leave.getCurrentNode();
                    break;
                }

            }
            if (leave != null) {
                //获取当前节点的审批人
                FlowNode flowNode = flowNodeService.getFlowNodeById(flowNodeNo);
                //获取审批人
                Employee employee = employeeService.getEmployeeByNo(flowNode.getApproveNo());
                //获取申请人
                Employee creator = employeeService.getEmployeeByNo(leave.getEmployeeNo());
                Map<String, Object> mail = new HashMap<>();
                mail.put("to", employee.getMail());
                mail.put("name", employee.getName());
                mail.put("cc", creator.getMail());
                mail.put("flowNo", leave.getFlowNo());
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME,
                        mail, new CorrelationData(leave.getFlowNo()));
            }

        }else if ("updateLeaveFlow".equals(methodName)){
            Object[] paramValues = joinPoint.getArgs();
            Approve approve = (Approve) paramValues[0];
            Leave leave = leaveService.getLeaveById(approve.getBusinessNo());
            //获取下一个审批人
            FlowNode flowNode = flowNodeService.getFlowNodeById(leave.getCurrentNode());
            //邮件内容
        }


    }
}
