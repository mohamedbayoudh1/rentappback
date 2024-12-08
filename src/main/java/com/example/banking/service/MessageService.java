package com.example.banking.service;

import com.example.banking.entities.Message;
import com.example.banking.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Save a message to the database
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    // Get chat history between two users, using the custom query in MessageRepository
    public List<Message> getChatHistory(Long userId, Long contactId) {
        return messageRepository.findChatHistory(userId, contactId);
    }

    // Mark a message as read
    public void markMessageAsRead(Long messageId) {
        messageRepository.markMessageAsRead(messageId);
    }
}
