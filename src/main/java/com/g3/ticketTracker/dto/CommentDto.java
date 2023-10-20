package com.g3.ticketTracker.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

	private Long id;

	@NotEmpty
	private String name;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String content;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

}
