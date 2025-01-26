package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.application.port.out.ChatRoomPersistencePort;
import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.domain.ChatRoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ChatRoomPersistenceAdapter.class, ChatRoomMapper.class})
class ChatRoomPersistenceAdapterTest {

    @Autowired
    private ChatRoomPersistencePort chatRoomPersistencePort;

    @Test
    @DisplayName("RDB에 채팅방을 저장하고, 저장 결과를 도메인 객체로 반환")
    void saveChatRoomTest() {
        ChatRoom newRoom = ChatRoom.builder()
                .roomType(ChatRoomType.ONE_TO_ONE)
                .roomName("1:1 Test Room")
                .active(true)
                .build();

        ChatRoom saved = chatRoomPersistencePort.save(newRoom);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRoomName()).isEqualTo("1:1 Test Room");
    }

    @Test
    @DisplayName("활성화된 방만 조회 테스트")
    void findActiveRoomsTest() {
        ChatRoom activeRoom = chatRoomPersistencePort.save(
                ChatRoom.builder().roomType(ChatRoomType.GROUP).roomName("ActiveRoom").active(true).build()
        );
        ChatRoom inactiveRoom = chatRoomPersistencePort.save(
                ChatRoom.builder().roomType(ChatRoomType.GROUP).roomName("InactiveRoom").active(false).build()
        );
        List<ChatRoom> activeRooms = chatRoomPersistencePort.findActiveRooms();

        assertThat(activeRooms).hasSize(1);
        assertThat(activeRooms.get(0).getRoomName()).isEqualTo("ActiveRoom");
    }
}
