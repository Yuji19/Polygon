package com.yuji.polygon.advice;

import com.yuji.polygon.entity.Approve;
import com.yuji.polygon.entity.FlowNode;
import com.yuji.polygon.entity.Leave;
import com.yuji.polygon.service.FlowNodeService;
import com.yuji.polygon.service.LeaveService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    FlowNodeService flowNodeService;

    @Autowired
    LeaveService leaveService;

    @Pointcut("execution(public * com.yuji.polygon.service.LeaveService.*(..))")
    public void serviceMail(){}

    @AfterReturning(pointcut = "serviceMail()",returning = "result")
    public void mailAfterService(JoinPoint joinPoint, Object result){
        //判断方法
        String methodName = joinPoint.getSignature().getName();
        if ("addLeaveFlow".equals(methodName)){
            int flowNodeNo = 0;
            Leave leave;
            //获取参数值
            Object[] paramValues = joinPoint.getArgs();
            for (int i = 0; i < paramValues.length; i++){
                if (paramValues[i] instanceof Leave){
                    leave = (Leave) paramValues[i];
                    flowNodeNo = leave.getCurrentNode();
                    break;
                }

            }
            //获取当前节点的审批人
            FlowNode flowNode = flowNodeService.getFlowNodeById(flowNodeNo);
            //邮件内容

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
