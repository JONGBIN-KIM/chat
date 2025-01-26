package com.makestar.chat.infrastructure.persistence.repository;

import com.makestar.chat.infrastructure.persistence.entity.ChatParticipantEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class InMemoryChatParticipantRepository {

    private final Map<Long, Set<ChatParticipantEntity>> store
            = new ConcurrentHashMap<>();

    public ChatParticipantEntity save(ChatParticipantEntity entity) {
        store.putIfAbsent(entity.getRoomId(), new CopyOnWriteArraySet<>());
        store.get(entity.getRoomId()).add(entity);
        return entity;
    }

    public Set<ChatParticipantEntity> findByRoomId(Long roomId) {
        return store.getOrDefault(roomId, Collections.emptySet());
    }
}
