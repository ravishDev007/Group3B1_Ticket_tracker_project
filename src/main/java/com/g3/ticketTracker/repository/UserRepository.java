package com.g3.ticketTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g3.ticketTracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
	

}
