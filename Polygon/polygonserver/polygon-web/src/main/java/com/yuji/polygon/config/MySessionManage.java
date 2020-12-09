package com.yuji.polygon.config;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: MySessionManage
 * @description: 自定义会话管理器
 * @author: yuji
 * @create: 2020-12-04 10:01:00
 */

@Component
public class MySessionManage {

    private Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    public void createdSession(String id, HttpSession session){
        System.out.println("create "+id);
        sessionMap.put(id,session);
    }

    public void destroyedSession(String id){
        System.out.println("destroy "+id);
        sessionMap.remove(id);
    }

    public HttpSession getSession(String id){
        System.out.println("sessionMap: "+sessionMap);
        return sessionMap.get(id);
    }
}
