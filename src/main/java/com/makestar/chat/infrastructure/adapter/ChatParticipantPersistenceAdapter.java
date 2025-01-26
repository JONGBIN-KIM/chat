package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.application.port.out.ChatParticipantPersistencePort;
import com.makestar.chat.domain.ChatParticipant;
import com.makestar.chat.infrastructure.persistence.entity.ChatParticipantEntity;
import com.makestar.chat.infrastructure.persistence.repository.InMemoryChatParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatParticipantPersistenceAdapter implements ChatParticipantPersistencePort {

    private final InMemoryChatParticipantRepository inMemoryRepo;
    private final ChatParticipantMapper mapper;

    @Override
    public ChatParticipant save(ChatParticipant participant) {
        ChatParticipantEntity entity = mapper.toEntity(participant);
        ChatParticipantEntity saved = inMemoryRepo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<ChatParticipant> findByRoomId(Long roomId) {
        return inMemoryRepo.findByRoomId(roomId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
