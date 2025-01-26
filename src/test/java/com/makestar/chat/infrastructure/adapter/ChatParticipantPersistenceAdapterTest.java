package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.domain.ChatParticipant;
import com.makestar.chat.infrastructure.persistence.repository.InMemoryChatParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({ ChatParticipantPersistenceAdapter.class, ChatParticipantMapper.class, InMemoryChatParticipantRepository.class })
class ChatParticipantPersistenceAdapterTest {

    private ChatParticipantPersistenceAdapter adapter;

    @BeforeEach
    void setup() {
        InMemoryChatParticipantRepository repo = new InMemoryChatParticipantRepository();
        ChatParticipantMapper mapper = new ChatParticipantMapper();
        adapter = new ChatParticipantPersistenceAdapter(repo, mapper);
    }

    @Test
    @DisplayName(" 참가자 save 후, 다시 조회하면 해당 참여자가 존재")
    void saveAndFindByRoomIdTest() {
        ChatParticipant participant = ChatParticipant.builder()
                .roomId(123L)
                .nickname("TestUser")
                .build();

        ChatParticipant saved = adapter.save(participant);

        assertThat(saved.getRoomId()).isEqualTo(123L);
        assertThat(saved.getNickname()).isEqualTo("TestUser");

        List<ChatParticipant> list = adapter.findByRoomId(123L);

        assertThat(list).hasSize(1);
        assertThat(list.get(0).getNickname()).isEqualTo("TestUser");
    }
}
