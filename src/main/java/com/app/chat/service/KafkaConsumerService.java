package com.app.chat.service;

import com.app.chat.entity.Constants;
import com.app.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = Constants.TOPIC, groupId = Constants.GROUP)
    public void consumeMessage(ChatMessage message){
        messagingTemplate.convertAndSend("/topic/public", message);
        log.info("Message received: {}", message);
    }
}
