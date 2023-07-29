package com.yuiybishel.Otix.repository;

import com.yuiybishel.Otix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}