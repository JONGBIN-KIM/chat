package com.makestar.chat.application.service;

import com.makestar.chat.application.port.out.ChatRoomPersistencePort;
import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.domain.ChatRoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class ChatRoomServiceTest {

    @Mock
    private ChatRoomPersistencePort mockPersistencePort;

    private ChatRoomService chatRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.chatRoomService = new ChatRoomService(mockPersistencePort);
    }

    @Test
    @DisplayName("채팅방 생성 시, active=true로 생성이 되고, 결과 도메인 객체를 반환한다")
    void createRoomTest() {
        ChatRoom roomToSave = ChatRoom.builder()
                .roomType(ChatRoomType.GROUP)
                .roomName("테스트방")
                .active(true)
                .build();

        ChatRoom savedRoom = ChatRoom.builder()
                .id(1L)
                .roomType(ChatRoomType.GROUP)
                .roomName("테스트방")
                .active(true)
                .build();

        given(mockPersistencePort.save(
                argThat(r -> r.getRoomType() == ChatRoomType.GROUP && "테스트방".equals(r.getRoomName()))
        )).willReturn(savedRoom);

        ChatRoom result = chatRoomService.createRoom(ChatRoomType.GROUP, "테스트방");

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.isActive()).isTrue();
        assertThat(result.getRoomName()).isEqualTo("테스트방");

        then(mockPersistencePort).should(times(1)).save(any(ChatRoom.class));
    }

    @Test
    @DisplayName("활성화된 채팅방 목록을 가져온다")
    void getActiveRoomsTest() {
        ChatRoom room1 = ChatRoom.builder().id(1L).roomName("Room1").active(true).build();
        ChatRoom room2 = ChatRoom.builder().id(2L).roomName("Room2").active(true).build();

        given(mockPersistencePort.findActiveRooms()).willReturn(List.of(room1, room2));

        List<ChatRoom> rooms = chatRoomService.getActiveRooms();
        assertThat(rooms).hasSize(2);
        assertThat(rooms.get(0).getRoomName()).isEqualTo("Room1");
        assertThat(rooms.get(1).getRoomName()).isEqualTo("Room2");
    }
}
