package com.example.banking.controllers;

import com.example.banking.entities.Message;
import com.example.banking.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Endpoint to get the chat history between two users
    @GetMapping("/history/{userId}/{contactId}")
    public List<Message> getChatHistory(@PathVariable Long userId, @PathVariable Long contactId) {
        return messageService.getChatHistory(userId, contactId);
    }

    // Endpoint to send a message (using a POST request)
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    // Endpoint to mark a message as read
    @PutMapping("/{messageId}/read")
    public void markMessageAsRead(@PathVariable Long messageId) {
        messageService.markMessageAsRead(messageId);
    }
}
