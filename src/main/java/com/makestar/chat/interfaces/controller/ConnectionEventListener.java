package com.makestar.chat.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

@Slf4j
@Component
public class ConnectionEventListener {

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        log.info("웹소켓 연결 완료: session={}", event.getMessage());
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("웹소켓 연결 해제: session={}, close status={}",
                event.getSessionId(), event.getCloseStatus());
    }
}
