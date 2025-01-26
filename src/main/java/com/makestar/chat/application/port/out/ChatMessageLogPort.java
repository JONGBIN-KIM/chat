package com.makestar.chat.application.port.out;

import com.makestar.chat.domain.ChatMessage;

public interface ChatMessageLogPort {
    void logMessage(ChatMessage message);
}
