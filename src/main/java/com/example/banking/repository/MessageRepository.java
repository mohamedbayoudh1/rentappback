package com.example.banking.repository;

import com.example.banking.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Custom query to fetch chat history between two users
    @Query("SELECT m FROM Message m WHERE (m.senderId = :userId AND m.receiverId = :contactId) " +
            "OR (m.senderId = :contactId AND m.receiverId = :userId) ORDER BY m.timestamp ASC")
    List<Message> findChatHistory(Long userId, Long contactId);

    // Optional: You can also add methods to mark messages as read
    @Query("UPDATE Message m SET m.isRead = true WHERE m.id = :messageId")
    void markMessageAsRead(Long messageId);
}
