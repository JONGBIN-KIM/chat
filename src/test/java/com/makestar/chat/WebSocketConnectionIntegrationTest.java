package com.makestar.chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WebSocketConnectionIntegrationTest {

    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;

    @BeforeEach
    void setUp() {
        List<Transport> transports = List.of(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);

        this.stompClient = new WebSocketStompClient(sockJsClient);
    }

    @Test
    @DisplayName("웹소켓 연결: connectAsync() + CompletableFuture")
    void testWebSocketConnection() throws Exception {

        String url = "ws://localhost:" + port + "/ws";

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

        CompletableFuture<StompSession> future = stompClient.connectAsync(
                url,
                headers,
                new TestStompSessionHandler()
        );

        StompSession session = future.get(3, TimeUnit.SECONDS);

        assertThat(session.isConnected()).isTrue();
    }

    static class TestStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("Connected! sessionId=" + session.getSessionId());
        }
        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            exception.printStackTrace();
        }
    }
}
