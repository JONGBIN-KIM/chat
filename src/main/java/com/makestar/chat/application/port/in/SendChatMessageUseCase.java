package com.makestar.chat.application.port.in;

import com.makestar.chat.domain.ChatMessage;

public interface SendChatMessageUseCase {
    ChatMessage sendChatMessage(ChatMessage message);
}
