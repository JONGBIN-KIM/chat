package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.application.port.out.ChatRoomPersistencePort;
import com.makestar.chat.domain.ChatRoom;
import com.makestar.chat.infrastructure.persistence.entity.ChatRoomEntity;
import com.makestar.chat.infrastructure.persistence.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ChatRoomPersistencePort {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public ChatRoom save(ChatRoom domain) {
        return chatRoomMapper.toDomain(chatRoomRepository.save(chatRoomMapper.toEntity(domain)));
    }

    @Override
    public List<ChatRoom> findActiveRooms() {
        List<ChatRoomEntity> entities = chatRoomRepository.findAllByActiveTrue();

        return entities.stream()
                .map(chatRoomMapper::toDomain)
                .collect(Collectors.toList());
    }
}
