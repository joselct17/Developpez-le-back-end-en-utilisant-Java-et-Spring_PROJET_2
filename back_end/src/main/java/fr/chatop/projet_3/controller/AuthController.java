package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.config.JWTService;
import fr.chatop.projet_3.model.AuthResponse;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final JWTService jwtService;
  private final IUserService usersService;


  @PostMapping("/login")
  public ResponseEntity<?> getToken(Authentication authentication) {
    String token = jwtService.generateToken(authentication);
    return ResponseEntity.ok(new AuthResponse(token));
  }



  @PostMapping("/register")
  public ResponseEntity<Users> createUser(@RequestBody Users users) {

    Users createdUser = usersService.createUser(users);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @GetMapping("/me")
  public ResponseEntity<Users> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String email = authentication.getName();
      Users currentUser = usersService.getUserByEmail(email);
      return ResponseEntity.ok(currentUser);
  }

}
