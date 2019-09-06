package com.springboot.repository;

import com.springboot.entity.UserEntity;
import com.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
