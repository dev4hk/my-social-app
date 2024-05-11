package org.example.mysocialapp.repository;

import org.example.mysocialapp.entity.Chat;
import org.example.mysocialapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findByUsersId(Integer userId);

    @Query("SELECT c from Chat c WHERE :user MEMBER of c.users AND :reqUser MEMBER of c.users")
    Optional<Chat> findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);

}
