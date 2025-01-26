package com.makestar.chat.application.service;

import com.makestar.chat.application.port.in.CreateChatRoomUseCase;
import com.makestar.chat.application.port.in.GetActiveChatRoomsUseCase;
import com.makestar.chat.application.port.out.ChatRoomPersistencePort;
import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.domain.ChatRoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements CreateChatRoomUseCase, GetActiveChatRoomsUseCase {

    private final ChatRoomPersistencePort chatRoomPersistencePort;

    @Override
    public ChatRoom createRoom(ChatRoomType roomType, String roomName) {
        ChatRoom newRoom = ChatRoom.builder()
                .roomType(roomType)
                .roomName(roomName)
                .active(true)
                .build();
        return chatRoomPersistencePort.save(newRoom);
    }

    @Override
    public List<ChatRoom> getActiveRooms() {
        return chatRoomPersistencePort.findActiveRooms();
    }
}