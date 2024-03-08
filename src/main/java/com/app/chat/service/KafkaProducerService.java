package com.app.chat.service;

import com.app.chat.entity.Constants;
import com.app.chat.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, ChatMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ChatMessage data) {
        kafkaTemplate.send(Constants.TOPIC, data);
    }
}
