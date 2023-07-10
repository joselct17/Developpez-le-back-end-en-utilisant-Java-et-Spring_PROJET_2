package com.openclassrooms.api.services;


import com.openclassrooms.api.dtos.AuthRequestDTO;
import com.openclassrooms.api.dtos.AuthResponseDTO;
import com.openclassrooms.api.dtos.RegisterRequestDTO;
import com.openclassrooms.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthService() {
	}

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public AuthResponseDTO register(RegisterRequestDTO dto) {
		
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(bCryptPasswordEncoder().encode(dto.getPassword()));
		
		userService.create(user);
		
		return new AuthResponseDTO(jwtService.generateToken(user.getEmail()));
	}

	public AuthResponseDTO authenticate(AuthRequestDTO dto) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						dto.getEmail(), 
						dto.getPassword()));
		
		final User user = userService.findByEmail(dto.getEmail());
		return new AuthResponseDTO(jwtService.generateToken(user.getEmail()));
	}
	
	
	
}
