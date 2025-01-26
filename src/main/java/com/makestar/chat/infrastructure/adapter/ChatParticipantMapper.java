package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.domain.ChatParticipant;
import com.makestar.chat.infrastructure.persistence.entity.ChatParticipantEntity;
import org.springframework.stereotype.Component;

@Component
public class ChatParticipantMapper {

    public ChatParticipant toDomain(ChatParticipantEntity e) {
        return ChatParticipant.builder()
                .roomId(e.getRoomId())
                .nickname(e.getNickname())
                .build();
    }

    public ChatParticipantEntity toEntity(ChatParticipant d) {
        return ChatParticipantEntity.builder()
                .roomId(d.getRoomId())
                .nickname(d.getNickname())
                .build();
    }
}
