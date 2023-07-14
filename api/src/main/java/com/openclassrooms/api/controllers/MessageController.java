package com.openclassrooms.api.controllers;

import com.openclassrooms.api.dtos.message.CreateMessageRequestDTO;
import com.openclassrooms.api.dtos.message.MessageDTO;
import com.openclassrooms.api.entities.Message;
import com.openclassrooms.api.entities.Rental;
import com.openclassrooms.api.entities.User;
import com.openclassrooms.api.services.MessageService;
import com.openclassrooms.api.services.RentalService;
import com.openclassrooms.api.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@Autowired
	private RentalService rentalService;

	@PostMapping("")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<String> create(@RequestBody CreateMessageRequestDTO dto, Authentication authentication) {
		final User user = userService.findById(dto.getUser_id());
		if(!Objects.equals(userService.findByEmail(authentication.getName()).getId(), user.getId())) {
			return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
		}
		final Rental rental = rentalService.findById(dto.getRental_id());
		final MessageDTO messageDTO = new MessageDTO(dto.getMessage(), user, rental);
		messageService.create(messageDTO);
		return ResponseEntity.ok("");
	}

}
