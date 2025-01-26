package com.makestar.chat.application.port.in;

import java.util.List;
import com.makestar.chat.domain.ChatRoom;

public interface GetActiveChatRoomsUseCase {
    List<ChatRoom> getActiveRooms();
}