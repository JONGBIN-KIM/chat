package com.makestar.chat.interfaces.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinChatRoomResponse {
    private Long roomId;
    private String nickname;
    private String resultMessage;
}