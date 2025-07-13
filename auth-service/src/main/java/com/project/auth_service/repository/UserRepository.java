package com.project.auth_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.auth_service.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}