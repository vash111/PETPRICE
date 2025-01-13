package com.petprice.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatRoomManager {

    private final ConcurrentHashMap<String, String> chatRooms = new ConcurrentHashMap<>();

    public String getOrCreateRoom(String productId, String userId) {
        String roomKey = productId + ":" + userId;
        chatRooms.putIfAbsent(roomKey, roomKey);
        return roomKey;
    }
}
