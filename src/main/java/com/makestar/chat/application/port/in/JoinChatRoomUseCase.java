package com.makestar.chat.application.port.in;

import com.makestar.chat.domain.ChatParticipant;

public interface JoinChatRoomUseCase {
    ChatParticipant join(Long roomId, String nickname);
}
