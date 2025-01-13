package com.petprice.repository;

import com.petprice.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomKeyOrderByTimestampAsc(String roomKey);
}
