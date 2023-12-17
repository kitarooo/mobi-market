package com.example.mobimarket.repository;

import com.example.mobimarket.entity.Product;
import com.example.mobimarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    //Optional<User> findByToken(String token);

    Optional<User> findAllById(Long id);
}
