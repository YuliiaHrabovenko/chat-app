package com.app.chat.config;

import com.app.chat.entity.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KafkaMessageDeserializer implements Deserializer<ChatMessage> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public ChatMessage deserialize(String s, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new String(bytes,
                    StandardCharsets.UTF_8), ChatMessage.class);
        } catch (Exception e) {
            throw new SerializationException("Error during deserialization");
        }
    }

    @Override
    public void close() {
    }
}
