package com.g3.ticketTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.g3.ticketTracker.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = " select c.* from comments c inner join tickets t \n" + 
	              "where c.ticket_id = t.id and created_by=:userId", nativeQuery = true)
	List<Comment> findCommentsByTicket(Long userId);
	
}
