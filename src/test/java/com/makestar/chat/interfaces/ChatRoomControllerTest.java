package com.makestar.chat.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.chat.application.port.in.CreateChatRoomUseCase;
import com.makestar.chat.application.port.in.GetActiveChatRoomsUseCase;
import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.domain.ChatRoomType;
import com.makestar.chat.interfaces.dto.ChatRoomCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
class ChatRoomControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CreateChatRoomUseCase createChatRoomUseCase;
    @Autowired
    GetActiveChatRoomsUseCase getActiveChatRoomsUseCase;

    @Autowired
    ObjectMapper objectMapper;

    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        CreateChatRoomUseCase createChatRoomUseCase() {
            return Mockito.mock(CreateChatRoomUseCase.class);
        }
        @Bean
        GetActiveChatRoomsUseCase getActiveChatRoomsUseCase() {
            return Mockito.mock(GetActiveChatRoomsUseCase.class);
        }
    }

    @BeforeEach
    void setup() {
        reset(createChatRoomUseCase, getActiveChatRoomsUseCase);
    }

    @Test
    @DisplayName("POST /api/chatrooms - 신규채팅방 개설")
    void createChatRoom() throws Exception {
        var req = new ChatRoomCreateRequest();
        req.setRoomType(ChatRoomType.GROUP);
        req.setRoomName("MockBean Room");

        var savedRoom = ChatRoom.builder()
                .id(999L)
                .roomType(ChatRoomType.GROUP)
                .roomName("MockBean Room")
                .active(true)
                .build();

        given(createChatRoomUseCase.createRoom(eq(ChatRoomType.GROUP), eq("MockBean Room")))
                .willReturn(savedRoom);

        mockMvc.perform(post("/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(999L))
                .andExpect(jsonPath("$.roomName").value("MockBean Room"))
                .andExpect(jsonPath("$.resultMessage").value("SUCCESS"));
    }

    @Test
    @DisplayName("GET /api/chatrooms/active 활성 채팅룸 확인")
    void getActiveChatRooms() throws Exception {
        var room1 = ChatRoom.builder().id(1L).roomName("Active1").active(true).build();
        var room2 = ChatRoom.builder().id(2L).roomName("Active2").active(true).build();

        given(getActiveChatRoomsUseCase.getActiveRooms()).willReturn(List.of(room1, room2));

        mockMvc.perform(get("/api/chatrooms/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rooms[0].roomId").value(1L))
                .andExpect(jsonPath("$.rooms[1].roomId").value(2L));
    }
}
