package com.makestar.chat.infrastructure.persistence.repository;

import com.makestar.chat.infrastructure.persistence.entity.ChatMessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageLogRepository extends JpaRepository<ChatMessageLog, Long> {
}
