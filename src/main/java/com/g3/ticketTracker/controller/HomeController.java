package com.g3.ticketTracker.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.g3.ticketTracker.dto.CommentDto;
import com.g3.ticketTracker.dto.TicketDto;
import com.g3.ticketTracker.service.TicketService;

@Controller
public class HomeController {

	private TicketService ticketService;

	public HomeController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	// handler method for http://localhost:8080/
	@GetMapping("/")
	public String viewTickets(Model model) {
		List<TicketDto> ticketsResponse = ticketService.findAllTickets();
		model.addAttribute("ticketsResponse", ticketsResponse);
		return "home/view_tickets";
	}

	// handler method to handle view ticket request from client
	@GetMapping("/ticket/{ticketUrl}")
	private String showTicket(@PathVariable("ticketUrl") String ticketUrl, Model model) {

		TicketDto ticket = ticketService.findTicketByUrl(ticketUrl);
		
		CommentDto commentDto = new CommentDto();
		model.addAttribute("ticket", ticket);
		model.addAttribute("comment", commentDto);
		return "home/client_ticket";

	}

	//handler method for client search request
	//http://localhost:8080/page/search?query=someQuery
	@GetMapping("/page/search")
	public String searchTickets(@RequestParam(value = "query") String query,
			                   Model model) {
		
		List<TicketDto> ticketsResponse = ticketService.searchTickets(query);
		model.addAttribute("ticketsResponse", ticketsResponse);
		return "home/view_tickets";
	}
	
}
