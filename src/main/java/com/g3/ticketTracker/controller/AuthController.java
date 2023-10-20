package com.g3.ticketTracker.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.g3.ticketTracker.dto.RegistrationDto;
import com.g3.ticketTracker.entity.User;
import com.g3.ticketTracker.service.UserService;

@Controller
public class AuthController {

	private UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	//handler method to handle log in request
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	// handle user registration request
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {

		RegistrationDto user = new RegistrationDto();
		model.addAttribute("user", user);
		return "register";
	}

	// handler method to handle user registration form submit request
	@PostMapping("/register/save")
	public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model) {

		User existingUser = userService.findByEmail(user.getEmail());
		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", "There is already a user with same email address");
		}
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		}
		userService.saveUser(user);
		return "redirect:/register?success";
	}
}
