package com.g3.ticketTracker.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.g3.ticketTracker.dto.TicketDto;
import com.g3.ticketTracker.entity.Ticket;
import com.g3.ticketTracker.entity.User;
import com.g3.ticketTracker.mapper.TicketMapper;
import com.g3.ticketTracker.repository.TicketRepository;
import com.g3.ticketTracker.repository.UserRepository;
import com.g3.ticketTracker.service.TicketService;
import com.g3.ticketTracker.util.SecurityUtils;

@Service
public class TicketServiceImpl implements TicketService {

	// @Autowired not required for one constructor from spring 3 onwards
	private TicketRepository ticketRepository;
	private UserRepository userRepository;

	public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository) {
		this.ticketRepository = ticketRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<TicketDto> findAllTickets() {

		List<Ticket> tickets = ticketRepository.findAll();

		// return tickets.stream().map((ticket) ->
		// TicketMapper.mapToTicketDto(ticket)).collect(Collectors.toList());

		return tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toList());
	}

	@Override
	public void createTicket(TicketDto ticketDto) {
		String email = SecurityUtils.getCurrentUser().getUsername();
		User user = userRepository.findByEmail(email);
		Ticket ticket = TicketMapper.mapToTicket(ticketDto);
		ticket.setCreatedBy(user);
		ticketRepository.save(ticket);

	}

	@Override
	public TicketDto findTickerById(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId).get();
		return TicketMapper.mapToTicketDto(ticket);
	}

	@Override
	public void updateTicket(TicketDto ticketDto) {
		String email = SecurityUtils.getCurrentUser().getUsername();
		User createdBy = userRepository .findByEmail(email);
		Ticket ticket = TicketMapper.mapToTicket(ticketDto);
		ticket.setCreatedBy(createdBy);
		ticketRepository.save(ticket);

	}

	@Override
	public void deleteTicket(Long ticketId) {
		ticketRepository.deleteById(ticketId);

	}

	@Override
	public TicketDto findTicketByUrl(String ticketUrl) {
		Ticket ticket = ticketRepository.findByUrl(ticketUrl).get();
		return TicketMapper.mapToTicketDto(ticket);
	}

	@Override
	public List<TicketDto> searchTickets(String query) {
		List<Ticket> tickets = ticketRepository.searchTickets(query);
		return tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toList());
	}

	@Override
	public List<TicketDto> findTicketByUser() {
		String email = SecurityUtils.getCurrentUser().getUsername();
		User createdBy = userRepository.findByEmail(email);
		Long userId = createdBy.getId();
		List<Ticket> tickets = ticketRepository.findTicketsByUser(userId);
		
		return tickets.stream()
				.map((ticket) -> TicketMapper.mapToTicketDto(ticket))
				.collect(Collectors.toList());
	}

}
