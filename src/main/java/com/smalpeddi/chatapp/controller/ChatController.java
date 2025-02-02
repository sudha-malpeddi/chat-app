package com.smalpeddi.chatapp.controller;

import com.smalpeddi.chatapp.dto.ChatMessage;
import com.smalpeddi.chatapp.dto.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {
    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    private final ChannelTopic topic;

    Logger log = LoggerFactory.getLogger(ChatController.class);

    public ChatController(RedisTemplate redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }
    @MessageMapping("/chat.sendChatMessage")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        //send message to the chat room
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        log.info("Sending chat message from: {}", chatMessage.getSender());
        redisTemplate.convertAndSend(topic.getTopic(), chatMessage);
        return chatMessage;
    }

    //add user to the chat room
    @MessageMapping("/chat.addUser")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        if (chatMessage == null ) {
            log.error("ChatMessage  is null!");
            return null; // Or handle the error gracefully (e.g., return an error message)
        }

        if ( chatMessage.getSender() == null) {
            log.error("sender is null!");
            return null; // Or handle the error gracefully (e.g., return an error message)
        }

        //Get user-name from the chatMessage object and add it to the websocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setType(MessageType.JOIN);
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        chatMessage.setMessage(chatMessage.getSender() + " joined the chat");
        log.info("User joined: {}", chatMessage.getSender());

        //send message to the chat room
        redisTemplate.convertAndSend(topic.getTopic(), chatMessage);
        return chatMessage;
    }
}
