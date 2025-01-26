package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.application.port.out.ChatMessageQueuePort;
import com.makestar.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component("popularAdapter")
@RequiredArgsConstructor
public class PopularChatMessageIntegrationAdapter implements ChatMessageQueuePort {

    private final MessageChannel popularChatMessageChannel;

    @Override
    public void produce(ChatMessage message) {
        popularChatMessageChannel.send(MessageBuilder.withPayload(message).build());
    }
}