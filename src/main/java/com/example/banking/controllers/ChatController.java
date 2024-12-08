package com.example.banking.controllers;

import com.example.banking.entities.Message;
import com.example.banking.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    // WebSocket endpoint for sending a message to the receiver
    @MessageMapping("/sendMessage")
    public void sendMessage(Message message) {
        messageService.saveMessage(message); // Save the message in the database
        // Send the message to the receiver via WebSocket
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), message);
    }
}
