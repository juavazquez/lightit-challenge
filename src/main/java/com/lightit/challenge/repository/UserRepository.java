package com.lightit.challenge.repository;

import com.lightit.challenge.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(String email);
}
