package com.makestar.chat.interfaces.dto;

import com.makestar.chat.domain.ChatRoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomCreateResponse {
    private Long roomId;
    private ChatRoomType roomType;
    private String roomName;
    private String resultMessage;
}