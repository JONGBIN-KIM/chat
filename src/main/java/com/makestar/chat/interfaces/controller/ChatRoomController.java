package com.makestar.chat.interfaces.controller;

import com.makestar.chat.application.port.in.CreateChatRoomUseCase;
import com.makestar.chat.application.port.in.GetActiveChatRoomsUseCase;
import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.interfaces.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Tag(name = "채팅방 API", description = "채팅방 개설 / 활성 채팅방 API")
@RestController
@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final CreateChatRoomUseCase createChatRoomUseCase;
    private final GetActiveChatRoomsUseCase getActiveChatRoomsUseCase;

    @Operation(summary = "채팅방 개설", description = "새 채팅방을 개설한다. (1:1, 그룹 등)")
    @PostMapping
    public ChatRoomCreateResponse createChatRoom(@RequestBody ChatRoomCreateRequest request) {
        ChatRoom createdRoom = createChatRoomUseCase.createRoom(request.getRoomType(), request.getRoomName());
        return ChatRoomCreateResponse.builder()
                .roomId(createdRoom.getId())
                .roomType(createdRoom.getRoomType())
                .roomName(createdRoom.getRoomName())
                .resultMessage("SUCCESS")
                .build();
    }

    @Operation(summary = "활성 채팅방 목록", description = "현재 활성화된 채팅방 리스트를 조회한다.")
    @GetMapping("/active")
    public ChatRoomListResponse getActiveChatRooms() {
        var rooms = getActiveChatRoomsUseCase.getActiveRooms();

        var listItems = rooms.stream()
                .map(r -> new ChatRoomListItem(r.getId(), r.getRoomType(), r.getRoomName()))
                .collect(Collectors.toList());

        return new ChatRoomListResponse(listItems);
    }
}
