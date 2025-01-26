package com.makestar.chat.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private Long roomId;
    private String sender;
    private String content;
    private long timestamp;
}
