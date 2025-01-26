package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.infrastructure.persistence.entity.ChatRoomEntity;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {

    public ChatRoomEntity toEntity(ChatRoom domain) {
        return ChatRoomEntity.builder()
                .id(domain.getId())
                .roomType(domain.getRoomType())
                .roomName(domain.getRoomName())
                .active(domain.isActive())
                .build();
    }

    public ChatRoom toDomain(ChatRoomEntity entity) {
        return ChatRoom.builder()
                .id(entity.getId())
                .roomType(entity.getRoomType())
                .roomName(entity.getRoomName())
                .active(entity.isActive())
                .build();
    }
}
