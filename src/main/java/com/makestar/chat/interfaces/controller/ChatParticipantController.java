package com.makestar.chat.interfaces.controller;

import com.makestar.chat.application.port.in.GetRoomParticipantsUseCase;
import com.makestar.chat.application.port.in.JoinChatRoomUseCase;
import com.makestar.chat.domain.ChatParticipant;
import com.makestar.chat.interfaces.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Tag(name = "채팅방 참여 API", description = "채팅방 참여 / 참여자 조회 API")
@RestController
@RequestMapping("/api/chatrooms/{roomId}/participants")
@RequiredArgsConstructor
public class ChatParticipantController {

    private final JoinChatRoomUseCase joinChatRoomUseCase;
    private final GetRoomParticipantsUseCase getRoomParticipantsUseCase;

    @Operation(summary = "채팅방 참여", description = "특정 방에 닉네임을 사용해 참여한다.")
    @PostMapping
    public JoinChatRoomResponse joinChatRoom(
            @PathVariable("roomId") Long roomId,
            @RequestBody JoinChatRoomRequest request
    ) {
        ChatParticipant joined = joinChatRoomUseCase.join(roomId, request.getNickname());
        return JoinChatRoomResponse.builder()
                .roomId(joined.getRoomId())
                .nickname(joined.getNickname())
                .resultMessage("SUCCESS")
                .build();
    }

    @Operation(summary = "채팅방 참여자 목록", description = "특정 방에 참여 중인 닉네임 리스트를 조회한다.")
    @GetMapping
    public ParticipantListResponse getParticipants(@PathVariable("roomId") Long roomId) {
        var domainList = getRoomParticipantsUseCase.getParticipants(roomId);

        var items = domainList.stream()
                .map(d -> new ParticipantListItem(d.getNickname()))
                .collect(Collectors.toList());

        return new ParticipantListResponse(items);
    }
}
