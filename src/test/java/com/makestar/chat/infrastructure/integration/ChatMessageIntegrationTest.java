package com.makestar.chat.infrastructure.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.chat.domain.ChatMessage;
import com.makestar.chat.interfaces.dto.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ChatMessageIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    QueueChannel chatMessageChannel;

    @BeforeEach
    void clearChannel() {
        Message<?> msg;
        while ((msg = chatMessageChannel.receive(0)) != null) {

        }
    }

    @Test
    @DisplayName("POST /api/chatMessages 로 메시지를 전송하면,인테그레이션에 메시지가 잘 들어감")
    void testMessageInputToQueue() throws Exception {
        ChatMessageRequest request = new ChatMessageRequest();
        request.setRoomId(123L);
        request.setSender("tester");
        request.setContent("메이크스타 채팅메시지 처리를위한 컨텐츠");

        mockMvc.perform(post("/api/chatMessages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(123L))
                .andExpect(jsonPath("$.sender").value("tester"))
                .andExpect(jsonPath("$.content").value("메이크스타 채팅메시지 처리를위한 컨텐츠"))
                .andExpect(jsonPath("$.timestamp").exists());

        Message<?> received = chatMessageChannel.receive(1000);

        Object payload = received.getPayload();
        assertThat(payload).isInstanceOf(ChatMessage.class);
        ChatMessage domainMsg = (ChatMessage) payload;

        assertThat(domainMsg.getRoomId()).isEqualTo(123L);
        assertThat(domainMsg.getSender()).isEqualTo("tester");
        assertThat(domainMsg.getContent()).isEqualTo("메이크스타 채팅메시지 처리를위한 컨텐츠");
        assertThat(domainMsg.getTimestamp()).isGreaterThan(0L);
    }
}
