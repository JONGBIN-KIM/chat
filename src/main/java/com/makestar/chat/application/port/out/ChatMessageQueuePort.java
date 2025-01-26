package com.makestar.chat.application.port.out;

import com.makestar.chat.domain.ChatMessage;

public interface ChatMessageQueuePort {
    void produce(ChatMessage message);
}
