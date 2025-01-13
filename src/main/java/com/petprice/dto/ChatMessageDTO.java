package com.petprice.dto;

import com.petprice.constant.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDTO {
    private String roomKey; // 채팅방 키
    private String sender; // 발신자
    private String content; // 메시지 내용
    private MessageType type; // 메시지 유형
    private LocalDateTime timestamp; // 메시지 시간
}
