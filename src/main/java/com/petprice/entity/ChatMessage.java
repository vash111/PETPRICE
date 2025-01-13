package com.petprice.entity;

import com.petprice.constant.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomKey; // 채팅방 키

    @Column(nullable = false)
    private String sender; // 발신자 이름

    @Column(nullable = false, length = 1000)
    private String content; // 메시지 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type; // 메시지 유형

    @Column(nullable = false)
    private LocalDateTime timestamp; // 메시지 시간
}
