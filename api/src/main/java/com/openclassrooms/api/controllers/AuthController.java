package com.openclassrooms.api.controllers;

import com.openclassrooms.api.dtos.AuthRequestDTO;
import com.openclassrooms.api.dtos.AuthResponseDTO;
import com.openclassrooms.api.dtos.GetUserResponseDTO;
import com.openclassrooms.api.dtos.RegisterRequestDTO;
import com.openclassrooms.api.entities.User;
import com.openclassrooms.api.services.AuthService;
import com.openclassrooms.api.services.JwtService;
import com.openclassrooms.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
		return ResponseEntity.ok(authService.register(dto));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO dto) {
		return ResponseEntity.ok(authService.authenticate(dto));
	}

	@GetMapping("/me")
	public GetUserResponseDTO me(@RequestHeader String authorization) {
		final String token = authorization.substring(7);
		final String mail = jwtService.getClaims(token).getSubject();
		final User user = userService.findByEmail(mail);
		return new GetUserResponseDTO(user);
	}
}
