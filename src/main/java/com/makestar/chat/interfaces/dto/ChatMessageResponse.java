package com.makestar.chat.interfaces.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponse {
    private Long roomId;
    private String sender;
    private String content;
    private long timestamp;
}
