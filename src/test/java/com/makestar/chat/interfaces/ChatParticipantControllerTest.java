package com.makestar.chat.interfaces;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.chat.application.port.in.GetRoomParticipantsUseCase;
import com.makestar.chat.application.port.in.JoinChatRoomUseCase;
import com.makestar.chat.domain.ChatParticipant;
import com.makestar.chat.interfaces.dto.JoinChatRoomRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.makestar.chat.interfaces.controller.ChatParticipantController;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ChatParticipantControllerTest {

    private JoinChatRoomUseCase joinChatRoomUseCase = Mockito.mock(JoinChatRoomUseCase.class);
    private GetRoomParticipantsUseCase getRoomParticipantsUseCase = Mockito.mock(GetRoomParticipantsUseCase.class);

    private ChatParticipantController controller;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        controller = new ChatParticipantController(joinChatRoomUseCase, getRoomParticipantsUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    @DisplayName("POST /api/chatrooms/{roomId}/participants - 참여 요청")
    void joinChatRoomTest() throws Exception {
        Long roomId = 101L;
        JoinChatRoomRequest req = new JoinChatRoomRequest();
        req.setNickname("testerA");

        ChatParticipant joined = ChatParticipant.builder()
                .roomId(roomId)
                .nickname("testerA")
                .build();

        when(joinChatRoomUseCase.join(eq(roomId), eq("testerA")))
                .thenReturn(joined);

        mockMvc.perform(post("/api/chatrooms/{roomId}/participants", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(101L))
                .andExpect(jsonPath("$.nickname").value("testerA"))
                .andExpect(jsonPath("$.resultMessage").value("SUCCESS"));
    }

    @Test
    @DisplayName("GET /api/chatrooms/{roomId}/participants - 참여자 목록")
    void getParticipantsTest() throws Exception {
        Long roomId = 202L;
        ChatParticipant p1 = ChatParticipant.builder().roomId(roomId).nickname("u1").build();
        ChatParticipant p2 = ChatParticipant.builder().roomId(roomId).nickname("u2").build();

        when(getRoomParticipantsUseCase.getParticipants(eq(roomId)))
                .thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/chatrooms/{roomId}/participants", roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.participants[0].nickname").value("u1"))
                .andExpect(jsonPath("$.participants[1].nickname").value("u2"));
    }
}