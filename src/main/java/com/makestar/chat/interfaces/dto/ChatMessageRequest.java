package com.makestar.chat.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {

    @NotNull(message = "roomId는 필수 입력값입니다.")
    private Long roomId;


    @NotBlank(message = "sender는 필수 입력값입니다.")
    private String sender;

    @NotBlank(message = "content는 필수 입력값입니다.")
    @Size(max = 1000, message = "메시지 내용은 1,000자를 초과할 수 없습니다.")
    private String content;
}
