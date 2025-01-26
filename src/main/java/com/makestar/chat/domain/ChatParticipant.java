package com.makestar.chat.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatParticipant {
    private Long roomId;
    private String nickname;
}
