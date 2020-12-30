package com.yuji.polygon.advice;

import com.yuji.polygon.entity.Approve;
import com.yuji.polygon.entity.Leave;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

    @Pointcut("execution(public * com.yuji.polygon.service.LeaveService.*(..))")
    public void serviceMail(){}

    @AfterReturning(pointcut = "serviceMail()",returning = "result")
    public void mailAfterService(JoinPoint joinPoint, Object result){
        //判断方法
        String methodName = joinPoint.getSignature().getName();
        if ("addLeaveFlow".equals(methodName)){
            int flowNodeNo = 0;
            //获取参数值
            Object[] paramValues = joinPoint.getArgs();
            for (int i = 0; i < paramValues.length; i++){
                if (paramValues[i] instanceof Leave){
                    flowNodeNo = ((Leave) paramValues[i]).getCurrentNode();
                    break;
                }

            }

        }else if ("updateLeaveFlow".equals(methodName)){
            Object[] paramValues = joinPoint.getArgs();
            Approve approve = (Approve) paramValues[0];

        }


    }
}
