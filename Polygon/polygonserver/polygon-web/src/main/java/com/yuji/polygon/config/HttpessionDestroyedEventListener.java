package com.yuji.polygon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 * @className: HttpessionDestroyedEventListener
 * @description: 监听会话销毁事件，把销毁的会话从自定义的会话管理中移除
 * @author: yuji
 * @create: 2020-12-04 10:06:00
 */

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
