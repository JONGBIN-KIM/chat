package com.makestar.chat.interfaces.controller;

import com.makestar.chat.application.port.in.SendChatMessageUseCase;
import com.makestar.chat.domain.ChatMessage;
import com.makestar.chat.interfaces.dto.ChatMessageRequest;
import com.makestar.chat.interfaces.dto.ChatMessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "채팅 메시지 API", description = "채팅방에서 메시지 송수신 API")
@RestController
@RequestMapping("/api/chatMessages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final SendChatMessageUseCase sendChatMessageUseCase;

    @Operation(summary = "채팅 메시지 전송", description = "특정 채팅방에 메시지를 전송한다.")
    @PostMapping
    public ChatMessageResponse sendMessage(@RequestBody ChatMessageRequest request) {
        ChatMessage msg = ChatMessage.builder()
                .roomId(request.getRoomId())
                .sender(request.getSender())
                .content(request.getContent())
                .build();

        ChatMessage result = sendChatMessageUseCase.sendChatMessage(msg);

        return ChatMessageResponse.builder()
                .roomId(result.getRoomId())
                .sender(result.getSender())
                .content(result.getContent())
                .timestamp(result.getTimestamp())
                .build();
    }
}
