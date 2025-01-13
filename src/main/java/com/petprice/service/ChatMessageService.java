package com.petprice.service;

import com.petprice.dto.ChatMessageDTO;
import com.petprice.entity.ChatMessage;
import com.petprice.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void saveMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessage messageEntity = new ChatMessage();
        messageEntity.setRoomKey(chatMessageDTO.getRoomKey());
        messageEntity.setSender(chatMessageDTO.getSender());
        messageEntity.setContent(chatMessageDTO.getContent());
        messageEntity.setType(chatMessageDTO.getType());
        messageEntity.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(messageEntity);
    }

    public List<ChatMessageDTO> getMessagesByRoomKey(String roomKey) {
        return chatMessageRepository.findByRoomKeyOrderByTimestampAsc(roomKey).stream()
                .map(msg -> {
                    ChatMessageDTO dto = new ChatMessageDTO();
                    dto.setRoomKey(msg.getRoomKey());
                    dto.setSender(msg.getSender());
                    dto.setContent(msg.getContent());
                    dto.setType(msg.getType());
                    dto.setTimestamp(msg.getTimestamp());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
