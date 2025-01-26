package com.makestar.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.chat.domain.ChatMessage;
import com.makestar.chat.interfaces.dto.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class BroadcastIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private WebSocketStompClient stompClient;
@BeforeEach
    void setupStompClient() {
        List<Transport> transports = Collections.singletonList(
                (Transport) new WebSocketTransport(new StandardWebSocketClient())
        );
        SockJsClient sockJsClient = new SockJsClient(transports);

        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        System.out.println(">>> [@BeforeEach] stompClient initialized: " + stompClient);
    }

    @Test
    @DisplayName("POST /api/chatMessages -> 인테그레이션 -> 컨슈머 -> WebSocket -> STOMP client => 브로드캐스트 과정 테스트")
    void testBroadcastFlow() throws Exception {

        String wsUrl = "ws://localhost:" + port + "/ws";
        CompletableFuture<StompSession> sessionFuture =
                stompClient.connectAsync(wsUrl, new WebSocketHttpHeaders(), new StompSessionHandlerAdapter() {});

        StompSession session = sessionFuture.get(5, TimeUnit.SECONDS);
        assertThat(session).isNotNull();
        assertThat(session.isConnected()).isTrue();
        System.out.println("STOMP connected: sessionId=" + session.getSessionId());

        Long roomId = 123L;
        String topicDest = "/topic/room/" + roomId;

        CompletableFuture<ChatMessage> receivedMessageFuture = new CompletableFuture<>();

        session.subscribe(topicDest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                ChatMessage chatMessage = (ChatMessage) payload;
                receivedMessageFuture.complete(chatMessage);
            }
        });

        ChatMessageRequest req = new ChatMessageRequest();
        req.setRoomId(roomId);
        req.setSender("tester");
        req.setContent("메이크스타 테스트 컨텐츠");

        mockMvc.perform(post("/api/chatMessages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(123))
                .andExpect(jsonPath("$.sender").value("tester"))
                .andExpect(jsonPath("$.content").value("메이크스타 테스트 컨텐츠"))
                .andExpect(jsonPath("$.timestamp").exists());

        ChatMessage broadcasted = receivedMessageFuture.get(5, TimeUnit.SECONDS);
        assertThat(broadcasted.getRoomId()).isEqualTo(123L);
        assertThat(broadcasted.getSender()).isEqualTo("tester");
        assertThat(broadcasted.getContent()).isEqualTo("메이크스타 테스트 컨텐츠");

        session.disconnect();
    }
}
