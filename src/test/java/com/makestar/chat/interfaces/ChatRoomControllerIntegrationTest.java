package com.makestar.chat.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.chat.domain.ChatRoomType;
import com.makestar.chat.interfaces.dto.ChatRoomCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ChatRoomControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createChatRoomIntegration() throws Exception {
        var req = new ChatRoomCreateRequest();
        req.setRoomType(ChatRoomType.GROUP);
        req.setRoomName("메이크스타 테스트 방제");

        mockMvc.perform(post("/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").exists())
                .andExpect(jsonPath("$.roomName").value("메이크스타 테스트 방제"))
                .andExpect(jsonPath("$.resultMessage").value("SUCCESS"));
    }
}
