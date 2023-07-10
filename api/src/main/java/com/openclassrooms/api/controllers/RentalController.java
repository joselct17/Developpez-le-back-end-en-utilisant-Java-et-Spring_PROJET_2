package com.openclassrooms.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.api.dtos.rental.CreateRentalRequestDTO;
import com.openclassrooms.api.dtos.rental.RentalDTO;
import com.openclassrooms.api.entities.Rental;
import com.openclassrooms.api.services.RentalService;
import com.openclassrooms.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	
	@Autowired
	private RentalService rentalService;

	@Autowired
	private UserService userService;


	@PostMapping("")
	public ResponseEntity<String> create(@RequestBody CreateRentalRequestDTO dto, Authentication authentication) {
		final String email = authentication.getName();
		final Integer ownerId = userService.findByEmail(email).getId();
		return ResponseEntity.ok(rentalService.create(dto, ownerId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RentalDTO> findById(@PathVariable Integer id) {
		final Rental rental = rentalService.findById(id);
		return ResponseEntity.ok(new RentalDTO(rental));
	}

	@GetMapping("")
	public ResponseEntity<List<RentalDTO>> findAll() {
		final List<Rental> rentals = rentalService.findAll();
		final List<RentalDTO> dtos = rentals.stream().map(RentalDTO::new).toList();
		return ResponseEntity.ok(dtos);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody CreateRentalRequestDTO dto, Authentication authentication) {
		final Rental rental = rentalService.findById(id);
		final Integer ownerId = userService.findByEmail(authentication.getName()).getId();
		if(!Objects.equals(rental.getOwnerId(), ownerId)) {
			return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Rental mergedEntity = objectMapper.convertValue(dto, Rental.class);
		mergedEntity.setId(rental.getId());
		mergedEntity.setOwnerId(rental.getOwnerId());
		return ResponseEntity.ok(rentalService.update(mergedEntity));
	}
}
