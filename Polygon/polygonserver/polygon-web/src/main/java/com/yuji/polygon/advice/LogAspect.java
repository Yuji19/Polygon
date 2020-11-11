package com.yuji.polygon.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yuji.polygon.utils.LogSqlUtil;

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

    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String tag = joinPoint.getSignature().getDeclaringTypeName();

        logger.info(tag,"url: "+request.getRequestURL());
        logger.info(tag,"params: "+ Arrays.toString(joinPoint.getArgs()));
        logger.info(tag,"method: "+ joinPoint.getSignature().getName());
    }

    //returning的值必须与方法的参数名一致,不然无法绑定
    @AfterReturning(pointcut = "controllerLog()",returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result){
        String tag = joinPoint.getSignature().getDeclaringTypeName();
        ObjectMapper json = new ObjectMapper();
        try {
            logger.info(tag,"result: "+json.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @After("mapperLog()")
    public void logBeforeMapper(JoinPoint joinPoint){
        String tag = joinPoint.getSignature().getDeclaringTypeName();
        logger.info(tag,"sql: "+ LogSqlUtil.getMybatisSql(joinPoint,sqlSessionFactory));
    }

}
