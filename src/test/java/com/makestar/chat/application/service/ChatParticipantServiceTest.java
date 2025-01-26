package com.makestar.chat.application.service;

import com.makestar.chat.application.port.out.ChatParticipantPersistencePort;
import com.makestar.chat.application.port.out.PopularRoomStatePort;
import com.makestar.chat.domain.ChatParticipant;
import com.makestar.chat.infrastructure.config.PopularityProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class ChatParticipantServiceTest {

    @Mock
    private ChatParticipantPersistencePort mockPersistencePort;

    @Mock
    private PopularRoomStatePort mockPopularRoomStatePort;

    @Mock
    private PopularityProperties mockPopularityProps;

    private ChatParticipantService participantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participantService = new ChatParticipantService(
                mockPersistencePort,
                mockPopularRoomStatePort,
                mockPopularityProps
        );
    }

    @Test
    @DisplayName("채팅방에 참여하면 ChatParticipant 도메인이 반환된다")
    void joinTest() {

        Long roomId = 100L;
        String nickname = "testerA";

        ChatParticipant participantToSave = ChatParticipant.builder()
                .roomId(roomId)
                .nickname(nickname)
                .build();

        ChatParticipant savedParticipant = ChatParticipant.builder()
                .roomId(roomId)
                .nickname(nickname)
                .build();

        given(mockPersistencePort.save(any(ChatParticipant.class)))
                .willReturn(savedParticipant);

        ChatParticipant result = participantService.join(roomId, nickname);

        assertThat(result.getRoomId()).isEqualTo(100L);
        assertThat(result.getNickname()).isEqualTo("testerA");

        then(mockPersistencePort).should(times(1)).save(any(ChatParticipant.class));
    }

    @Test
    @DisplayName("특정 방의 참가자 목록을 가져온다")
    void getParticipantsTest() {
        Long roomId = 200L;
        ChatParticipant p1 = ChatParticipant.builder().roomId(roomId).nickname("user1").build();
        ChatParticipant p2 = ChatParticipant.builder().roomId(roomId).nickname("user2").build();
        given(mockPersistencePort.findByRoomId(roomId)).willReturn(List.of(p1, p2));

        List<ChatParticipant> participants = participantService.getParticipants(roomId);

        assertThat(participants).hasSize(2);
        assertThat(participants.get(0).getNickname()).isEqualTo("user1");
        assertThat(participants.get(1).getNickname()).isEqualTo("user2");
    }
}
