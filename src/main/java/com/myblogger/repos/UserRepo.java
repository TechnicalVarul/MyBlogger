package com.myblogger.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogger.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String userEmail);

	
}
