package com.makestar.chat.application.port.in;

import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.domain.ChatRoomType;

public interface CreateChatRoomUseCase {
    ChatRoom createRoom(ChatRoomType roomType, String roomName);
}