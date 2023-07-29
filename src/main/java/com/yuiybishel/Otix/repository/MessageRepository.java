package com.yuiybishel.Otix.repository;

import com.yuiybishel.Otix.model.Message;
import com.yuiybishel.Otix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUser(User user);
}
