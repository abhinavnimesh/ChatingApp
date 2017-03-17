package com.example.animatorabhi.chatingapp.chat;

/**
 * Created by ANIMATOR ABHI on 10/10/2016.
 */

public class ChatMessage {
    String name;
    String message;

    public ChatMessage() {
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
