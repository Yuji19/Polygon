package com.yuji.polygon.util;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * @className: LogSqlUtil
 * @description: 用于解析mybatis的SQL语句的工具类
 * @author: 互联网
 * @create: 2020-10-26 21:37
 */
public class LogSqlUtil {

    //通过反射获取属性
    public static Map<String,Object> objectToMap(Object object){
        Map<String,Object> map = new HashMap<String, Object>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName,value);
        }
        return map;
    }

    public static String getMybatisSql(JoinPoint joinPoint, SqlSessionFactory sqlSessionFactory){
        Map<String,Object> map = new HashMap<>(8);
        //获取当前mapper的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String namespace = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Configuration configuration = sqlSessionFactory.getConfiguration();
        //MappedStatement对象对应Mapper.xml配置文件中的一个select/update/insert/delete节点，描述的就是一条SQL语句
        MappedStatement mappedStatement = configuration.getMappedStatement(namespace+"."+methodName);
        //获取方法的参数
        Object[] objects = joinPoint.getArgs();
        //获取方法参数的注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++){
            Object object = objects[i];
            //参数上无注解
            if (parameterAnnotations[i].length == 0){
                //判断参数类型是自定义实体类还是Map
                if (object.getClass().getClassLoader() == null && object instanceof Map){
                    map.putAll((Map<? extends String,?>) object);
                }else{
                    map.putAll(objectToMap(object));
                }
            }else{
                for (Annotation annotation : parameterAnnotations[i]){
                    if (annotation instanceof Param){
                        map.put(((Param) annotation).value(),object);
                    }
                }
            }
        }
        BoundSql boundSql = mappedStatement.getBoundSql(map);
        return showSql(configuration,boundSql);
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //匹配任意多个空白符并替换为空白字符串
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            //类型转换工具，把Java类型转为JDBC类型
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                //第一个占位符替换为实际参数值
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                //获取DateSource对象的描述
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    //判断元数据是否有该属性
                    if (metaObject.hasGetter(propertyName)) {
                        //获取对应属性的值
                        Object obj = metaObject.getValue(propertyName);
                        //第一个占位符替换为实际参数值
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        //第一个占位符替换为实际参数值
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    public static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String || obj instanceof Date || obj instanceof Timestamp) {
            value = "'" + obj.toString() + "'";
        }else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

}
