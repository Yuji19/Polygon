package com.yuji.polygon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

/**
 * @className: HttpSessionCreatedEventListener
 * @description: 监听会话创建事件，把创建的会话存到自定义的会话管理中
 * @author: yuji
 * @create: 2020-12-04 10:05:00
 */

@Component
public class HttpSessionCreatedEventListener implements ApplicationListener<HttpSessionCreatedEvent> {

    @Autowired
    MySessionManage sessionManage;

    @Override
    public void onApplicationEvent(HttpSessionCreatedEvent event) {
        String sessionId = event.getSession().getId();
        sessionManage.createdSession(sessionId, event.getSession());
    }
}
