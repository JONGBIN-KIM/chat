package com.makestar.chat.infrastructure.persistence.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatParticipantEntity {
    private Long roomId;
    private String nickname;
}
