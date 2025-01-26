package com.makestar.chat.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    private Long id;
    private ChatRoomType roomType;
    private String roomName;
    private boolean active;
}