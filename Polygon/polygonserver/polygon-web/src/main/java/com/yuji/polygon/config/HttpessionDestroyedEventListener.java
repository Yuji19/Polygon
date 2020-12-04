package com.yuji.polygon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class HttpessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {

    @Autowired
    MySessionManage sessionManage;

    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        String sessionId = event.getSession().getId();
        sessionManage.destroyedSession(sessionId);

    }
}
