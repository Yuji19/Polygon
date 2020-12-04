package com.yuji.polygon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

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
