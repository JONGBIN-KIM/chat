package com.makestar.chat.infrastructure.persistence.repository;

import com.makestar.chat.infrastructure.persistence.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    List<ChatRoomEntity> findAllByActiveTrue();
}
