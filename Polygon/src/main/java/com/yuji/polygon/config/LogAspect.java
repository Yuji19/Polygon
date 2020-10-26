package com.yuji.polygon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuji.polygon.util.LogSqlUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * @className: LogAspect
 * @description: 日志切面类
 * @author: yuji
 * @create: 2020-10-26 21:11
 **/

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    //创建切点
    @Pointcut("execution(public * com.yuji.polygon.controller.*.*(..))")
    public void controllerLog(){}

    @Pointcut("execution(public * com.yuji.polygon.mapper.*.*(..))")
    public void mapperLog(){}

    //环绕通知
    @Around("controllerLog()")
    public Object logAroundController(ProceedingJoinPoint joinPoint){
        //获取请求
        RequestAttribute requestAttribute = (RequestAttribute) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttribute).getRequest();

        logger.info("------URL: "+request.getRequestURL());
        logger.info("------PARAMS: "+ Arrays.toString(joinPoint.getArgs()));
        logger.info("------CLASS_METHOD: "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());

        //后端响应数据
        Object result = null;
        try {
            result = joinPoint.proceed();
            ObjectMapper json = new ObjectMapper();
            logger.info("------RESULT: "+json.writeValueAsString(result));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        return request;

    }


    @Around("mapperLog()")
    public Object logAroundMapper(ProceedingJoinPoint joinPoint){

        logger.info("------SQL: "+ LogSqlUtil.getMybatisSql(joinPoint,sqlSessionFactory));

        Object result = null;
        try {
            result = joinPoint.proceed();
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }

        return result;
    }

}
