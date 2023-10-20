package com.g3.ticketTracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.g3.ticketTracker.dto.CommentDto;
import com.g3.ticketTracker.dto.TicketDto;
import com.g3.ticketTracker.service.CommentService;
import com.g3.ticketTracker.service.TicketService;
import com.g3.ticketTracker.util.Role;
import com.g3.ticketTracker.util.SecurityUtils;

@Controller
public class TicketController {

	private TicketService ticketService;
	private CommentService commentService;

	// constructor based DI
	public TicketController(TicketService ticketService, CommentService commentService) {
		this.ticketService = ticketService;
		this.commentService = commentService;
	}

	@GetMapping("/admin/tickets")
	// create handler method, GET request and return model and view
	public String tickets(Model model) {
		String role = SecurityUtils.getRole();
		List<TicketDto> tickets = null;
		if (Role.ROLE_ADMIN.name().equals(role)) {
			tickets = ticketService.findAllTickets();
		} else {
			tickets = ticketService.findTicketByUser();
		}
		model.addAttribute("tickets", tickets); // key value pair
		return "/admin/tickets";
	}

	// handler method to handle new Ticket request
	@GetMapping("admin/tickets/newTicket")
	public String newTicketForm(Model model) {
		TicketDto ticketDto = new TicketDto();
		model.addAttribute("ticket", ticketDto);
		return "admin/create_ticket";
	}

	// handler method to handle form submit request
	@PostMapping("/admin/tickets")
	public String createTicket(@Valid @ModelAttribute("ticket") TicketDto ticketDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("ticket", ticketDto);
			return "admin/create_ticket";
		}
		ticketDto.setUrl(getUrl(ticketDto.getTitle()));
		ticketService.createTicket(ticketDto);
		return "redirect:/admin/tickets";
	}

	// handler method to handle list comments request
	@GetMapping("/admin/tickets/comments")
	public String postComments(Model model) {
		String role = SecurityUtils.getRole();
		List<CommentDto> comments = null;
        if(Role.ROLE_ADMIN.name().equals(role)) {
        	comments = commentService.findAllComments();
        }
        else {
        	 comments = commentService.findCommentsByTicket();
        }
		
		model.addAttribute("comments", comments);
		return "admin/comments";
	}

	@GetMapping("/admin/tickets/comments/{commentId}")
	public String deleteComment(@PathVariable("commentId") Long commentId) {
		commentService.deleteComment(commentId);
		return "redirect:/admin/tickets/comments";
	}

	private static String getUrl(String ticketTitle) {

		String title = ticketTitle.trim().toLowerCase();
		String url = title.replaceAll("\\s+", "-");
		url = url.replaceAll("[A-Za-z0-9]", "=");
		return url;
	}

	// handler method to handle edit ticket request
	@GetMapping("/admin/tickets/{ticketId}/edit")
	public String editTicketForm(@PathVariable("ticketId") Long ticketId, Model model) {

		TicketDto ticketDto = ticketService.findTickerById(ticketId);
		model.addAttribute("ticket", ticketDto);
		return "admin/edit_ticket";

	}

	// handler method to handle edit ticket form submit request
	@PostMapping("/admin/tickets/{ticketId}")
	public String updateTicket(@PathVariable("ticketId") long ticketId,
			@Valid @ModelAttribute("ticket") TicketDto ticket, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("ticket", ticket);
			return "admin/edit_ticket";
		}
		ticket.setId(ticketId);
		ticketService.updateTicket(ticket);
		return "redirect:/admin/tickets";
	}

	// handler method to delete the ticket request
	@GetMapping("/admin/tickets/{ticketId}/delete")

	public String deleteTicket(@PathVariable("ticketId") Long ticketId) {
		ticketService.deleteTicket(ticketId);
		return "redirect:/admin/tickets";
	}

	// handler method to view Ticket request
	@GetMapping("/admin/ticket/{ticketUrl}/view")
	public String viewPost(@PathVariable("ticketUrl") String ticketUrl, Model model) {
		TicketDto ticketDto = ticketService.findTicketByUrl(ticketUrl);
		model.addAttribute("ticket", ticketDto);
		return "admin/view_ticket";
	}

	// handler method to handle search tickets
	// localhost:8080/admin/tickets/search?query=tomcat
	@GetMapping("/admin/tickets/search")
	public String searchTickets(@RequestParam(value = "query") String query, Model model) {

		List<TicketDto> tickets = ticketService.searchTickets(query);
		model.addAttribute("tickets", tickets);
		return "admin/tickets";

	}

}