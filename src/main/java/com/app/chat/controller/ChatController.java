package com.app.chat.controller;

import com.app.chat.entity.ChatMessage;
import com.app.chat.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final KafkaProducerService kafkaProducerService;

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatMessage message, StompHeaderAccessor headerAccessor) {
        log.info("Message received from client: {}", message);
        String userId = headerAccessor.getFirstNativeHeader("userId");
        message.setUserId(userId);
        kafkaProducerService.sendMessage(message);
    }

    @MessageMapping("/addUser")
    public void addUser(@Payload ChatMessage message, StompHeaderAccessor headerAccessor) {
        log.info("Username received from client: {}", message);
        String userId = headerAccessor.getFirstNativeHeader("userId");
        message.setUserId(userId);
        message.setContent(message.getUserName() + " joined!");
        kafkaProducerService.sendMessage(message);
    }

}
