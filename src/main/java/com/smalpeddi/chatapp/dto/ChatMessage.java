package com.smalpeddi.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ChatMessage {
    private String message;
    private String timestamp;
    private String sender;
    private MessageType type;


    public ChatMessage() {}

    public ChatMessage(String message, String timestamp, String sender, MessageType type) {
        this.message = message;
        this.timestamp = timestamp;
        this.sender = sender;
        this.type = type;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
}
