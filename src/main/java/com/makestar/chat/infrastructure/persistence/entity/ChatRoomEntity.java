package com.makestar.chat.infrastructure.persistence.entity;

import com.makestar.chat.domain.ChatRoomType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatRoomType roomType;

    private String roomName;

    private boolean active;
}