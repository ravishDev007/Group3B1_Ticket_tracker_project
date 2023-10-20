package com.g3.ticketTracker.service;

import com.g3.ticketTracker.dto.RegistrationDto;
import com.g3.ticketTracker.entity.User;

public interface UserService {

	void saveUser(RegistrationDto registrationDto);

	User findByEmail(String email);
}
