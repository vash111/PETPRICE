package com.petprice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String sender; // 발신자
    private String content; // 메시지 내용
    private MessageType type; // 메시지 유형
    private String room;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
