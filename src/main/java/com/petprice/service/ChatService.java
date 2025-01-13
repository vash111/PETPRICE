package com.petprice.service;

import com.petprice.entity.ChatRoom;
import com.petprice.entity.Product;
import com.petprice.entity.User;
import com.petprice.repository.ChatRoomRepository;
import com.petprice.repository.ProductRepository;
import com.petprice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ChatRoom createChatRoom(String roomKey, Long productId, Long sellerId, Long buyerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다."));

        ChatRoom chatRoom = new ChatRoom(roomKey, product, seller, buyer);
        return chatRoomRepository.save(chatRoom);
    }
}
