package org.example.mysocialapp.repository;

import org.example.mysocialapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByChatId(Integer chatId);
}
