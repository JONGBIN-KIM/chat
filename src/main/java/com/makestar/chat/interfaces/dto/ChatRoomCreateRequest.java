package com.makestar.chat.interfaces.dto;

import com.makestar.chat.domain.ChatRoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateRequest {
    private ChatRoomType roomType;
    private String roomName;
}