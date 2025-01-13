package com.petprice.controller;

import com.petprice.dto.ChatMessage;
import com.petprice.dto.ChatMessageDTO;
import com.petprice.dto.ChatRoomRequest;
import com.petprice.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/chats")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    // 채팅방 생성
    @PostMapping("/create")
    public ResponseEntity<String> createChatRoom(@RequestBody ChatRoomRequest request) {
        // 채팅방 생성 로직 (예: 채팅방 키를 DB에 저장)
        String roomKey = request.getProductId() + "-" + request.getUserId(); // 예시: "productId-userId"

        // DB 저장 로직 추가 가능
        System.out.println("채팅방 생성됨: " + roomKey);

        return ResponseEntity.ok(roomKey); // 채팅방 키 반환
    }

    @GetMapping("/messages/{roomKey}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByRoomKey(@PathVariable String roomKey) {
        List<ChatMessageDTO> messages = chatMessageService.getMessagesByRoomKey(roomKey);
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDTO chatMessageDTO) {
        chatMessageService.saveMessage(chatMessageDTO);
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessageDTO.getRoomKey(), chatMessageDTO);
    }

    // 채팅방에 사용자 추가
    @MessageMapping("/chat.addUser")
    public void addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // WebSocket 세션에 사용자 이름 저장
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        // 사용자 추가 메시지를 브로드캐스트
        chatMessage.setContent(chatMessage.getSender() + " 님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessage.getRoom(), chatMessage);
    }

    // 채팅방 시작
    @MessageMapping("/chat.start")
    public void startChat(ChatMessage message) {
        // 채팅방 생성 로직 (DB 처리나 추가적인 설정이 필요한 경우 여기에 추가)
        String roomKey = message.getRoom();

        // 클라이언트에게 채팅방 연결 메시지 전송
        message.setContent("채팅방이 생성되었습니다: " + roomKey);
        messagingTemplate.convertAndSend("/topic/chat/" + roomKey, message);
    }
}
