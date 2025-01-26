package com.makestar.chat.infrastructure.config;

import com.makestar.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;


@Configuration
@EnableIntegration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class BroadcastIntegrationConfig {

    private final SimpMessageSendingOperations messagingTemplate;

    @ServiceActivator(inputChannel = "chatMessageChannel")
    public void broadcastNormalMessage(Message<?> message) {
        Object payload = message.getPayload();
        if (payload instanceof ChatMessage chatMessage) {
            String destination = "/topic/room/" + chatMessage.getRoomId();
            messagingTemplate.convertAndSend(destination, chatMessage);
            System.out.println("[NORMAL] Broadcasting ChatMessage = " + chatMessage);
        }
    }

    @ServiceActivator(inputChannel = "popularChatMessageChannel")
    public void broadcastPopularMessage(Message<?> message) {
        Object payload = message.getPayload();
        if (payload instanceof ChatMessage chatMessage) {
            String destination = "/topic/room/" + chatMessage.getRoomId();
            messagingTemplate.convertAndSend(destination, chatMessage);
            System.out.println("[POPULAR] Broadcasting ChatMessage = " + chatMessage);
        }
    }
}
