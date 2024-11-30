package com.projecteventplatform.glcc.repositories;

import com.projecteventplatform.glcc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email) ;
}