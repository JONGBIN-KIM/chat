package com.makestar.chat.application.service;

import com.makestar.chat.application.port.in.SendChatMessageUseCase;
import com.makestar.chat.application.port.out.ChatMessageLogPort;
import com.makestar.chat.application.port.out.ChatMessageQueuePort;
import com.makestar.chat.application.port.out.PopularRoomStatePort;
import com.makestar.chat.domain.ChatMessage;
import com.makestar.chat.infrastructure.config.PopularityProperties;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService implements SendChatMessageUseCase {

    @Qualifier("normalAdapter")
    private final ChatMessageQueuePort chatMessageQueuePort;

    @Qualifier("popularAdapter")
    private final ChatMessageQueuePort popularChatMessageQueuePort;

    private final PopularRoomStatePort popularRoomStatePort;

    private final PopularityProperties popularityProps;

    private final ChatMessageLogPort chatMessageLogPort;

    public ChatMessageService(
            @Qualifier("normalAdapter") ChatMessageQueuePort chatMessageQueuePort,
            @Qualifier("popularAdapter") ChatMessageQueuePort popularChatMessageQueuePort,
            PopularRoomStatePort popularRoomStatePort,
            PopularityProperties popularityProps,
            ChatMessageLogPort chatMessageLogPort
    ) {
        this.chatMessageQueuePort = chatMessageQueuePort;
        this.popularChatMessageQueuePort = popularChatMessageQueuePort;
        this.popularRoomStatePort = popularRoomStatePort;
        this.popularityProps = popularityProps;
        this.chatMessageLogPort = chatMessageLogPort;
    }

    @Override
    @Transactional
    public ChatMessage sendChatMessage(ChatMessage message) {
        message.setTimestamp(System.currentTimeMillis());

        Long roomId = message.getRoomId();
        double dropRate = popularityProps.getDropRate();

        if (popularRoomStatePort.isSuperPopular(roomId)) {
            if (Math.random() < dropRate) {
                System.out.println("사용자가 너무많아서 일부채팅 무시 : " + roomId);
                return message;
            }
            popularChatMessageQueuePort.produce(message);

        } else if (popularRoomStatePort.isPopular(roomId)) {
            popularChatMessageQueuePort.produce(message);

        } else {
            chatMessageQueuePort.produce(message);
        }


        chatMessageLogPort.logMessage(message);

        return message;
    }
}
