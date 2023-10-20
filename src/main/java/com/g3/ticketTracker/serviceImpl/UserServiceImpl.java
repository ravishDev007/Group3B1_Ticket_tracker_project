package com.g3.ticketTracker.serviceImpl;

import java.util.Arrays;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.g3.ticketTracker.dto.RegistrationDto;
import com.g3.ticketTracker.entity.Role;
import com.g3.ticketTracker.entity.User;
import com.g3.ticketTracker.repository.RoleRepository;
import com.g3.ticketTracker.repository.UserRepository;
import com.g3.ticketTracker.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(RegistrationDto registrationDto) {

		User user = new User();
		user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
		user.setEmail(registrationDto.getEmail());
		// use spring security to encrypt the password
		user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
		Role role = roleRepository.findByName("ROLE_GUEST");
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
