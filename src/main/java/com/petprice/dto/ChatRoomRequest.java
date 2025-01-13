package com.petprice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomRequest {
    private Long productId; // 상품 ID
    private Long userId;    // 사용자 ID
}
