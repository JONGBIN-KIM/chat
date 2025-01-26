package com.makestar.chat.application.port.in;

import com.makestar.chat.domain.ChatParticipant;

import java.util.List;

public interface GetRoomParticipantsUseCase {
    List<ChatParticipant> getParticipants(Long roomId);
}
