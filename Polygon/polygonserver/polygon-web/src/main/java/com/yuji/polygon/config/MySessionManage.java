package com.yuji.polygon.config;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MySessionManage {

    private Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    public void createdSession(String id, HttpSession session){
        sessionMap.put(id,session);
    }

    public void destroyedSession(String id){
        sessionMap.remove(id);
    }

    public HttpSession getSession(String id){
        System.out.println("sessionMap: "+sessionMap);
        return sessionMap.get(id);
    }
}
