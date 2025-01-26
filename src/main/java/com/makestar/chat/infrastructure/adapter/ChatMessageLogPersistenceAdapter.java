package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.application.port.out.ChatMessageLogPort;
import com.makestar.chat.domain.ChatMessage;
import com.makestar.chat.infrastructure.persistence.entity.ChatMessageLog;
import com.makestar.chat.infrastructure.persistence.repository.ChatMessageLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageLogPersistenceAdapter implements ChatMessageLogPort {

    private final ChatMessageLogRepository chatMessageLogRepository;

    @Override
    public void logMessage(ChatMessage message) {
        ChatMessageLog log = ChatMessageLog.builder()
                .roomId(message.getRoomId())
                .sender(message.getSender())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
        chatMessageLogRepository.save(log);
    }
}
