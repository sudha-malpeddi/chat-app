package com.smalpeddi.chatapp.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalpeddi.chatapp.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisMessageSubscriber implements MessageListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public RedisMessageSubscriber(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishedMsg =  redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessage chatMessage = objectMapper.readValue(publishedMsg, ChatMessage.class);
            simpMessageSendingOperations.convertAndSend("/topic/public", chatMessage);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }
}
