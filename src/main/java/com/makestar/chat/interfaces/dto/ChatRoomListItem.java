package com.makestar.chat.interfaces.dto;

import com.makestar.chat.domain.ChatRoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomListItem {
    private Long roomId;
    private ChatRoomType roomType;
    private String roomName;
}