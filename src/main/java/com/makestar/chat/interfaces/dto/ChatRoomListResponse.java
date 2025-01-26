package com.makestar.chat.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatRoomListResponse {
    private List<ChatRoomListItem> rooms;
}