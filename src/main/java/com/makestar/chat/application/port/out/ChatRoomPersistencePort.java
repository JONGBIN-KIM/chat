package com.makestar.chat.application.port.out;

import com.makestar.chat.domain.ChatRoom;

import java.util.List;

public interface ChatRoomPersistencePort {

    ChatRoom save(ChatRoom domain);

    List<ChatRoom> findActiveRooms();
}