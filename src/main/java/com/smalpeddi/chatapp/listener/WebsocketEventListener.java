package com.smalpeddi.chatapp.listener;

import com.smalpeddi.chatapp.controller.ChatController;
import com.smalpeddi.chatapp.dto.ChatMessage;
import com.smalpeddi.chatapp.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
//@RequiredArgsConstructor
//@Slf4j
public class WebsocketEventListener {
    private final RedisTemplate<String, Object> redisTemplate;
    Logger log = LoggerFactory.getLogger(WebsocketEventListener.class);

    public WebsocketEventListener(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @EventListener
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String username = (String)headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            log.info("User Disconnected: {}", username);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(MessageType.LEAVE);
            chatMessage.setSender(username);
            chatMessage.setMessage(username + " has left the chat");
            log.info("User disconnected:{}", chatMessage.getSender());

            redisTemplate.convertAndSend("chat", chatMessage);
        }
    }
}
