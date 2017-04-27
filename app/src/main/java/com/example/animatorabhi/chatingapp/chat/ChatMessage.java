package com.example.animatorabhi.chatingapp.chat;

/**
 * Created by ANIMATOR ABHI on 10/10/2016.
 */

public class ChatMessage {
    String name;
    String message;
    String sender;
    long timestamp;

    public ChatMessage() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ChatMessage(String name, String message, String sender, long timestamp) {
        this.name = name;
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public ChatMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
