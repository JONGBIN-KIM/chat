package com.makestar.chat.application.port.out;

import com.makestar.chat.domain.ChatParticipant;

import java.util.List;

public interface ChatParticipantPersistencePort {

    ChatParticipant save(ChatParticipant participant);

    List<ChatParticipant> findByRoomId(Long roomId);
}
