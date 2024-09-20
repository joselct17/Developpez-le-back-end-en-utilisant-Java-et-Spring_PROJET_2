package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.config.AuthenticationService;
import fr.chatop.projet_3.config.JWTService;
import fr.chatop.projet_3.model.AuthResponse;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.LoginDto;
import fr.chatop.projet_3.model.dto.UserDto;
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

  private final AuthenticationService authenticationService;
  private final IUserService usersService;


  @PostMapping("/login")
  public ResponseEntity<?> getToken(@RequestBody LoginDto loginDto) {
    String login = loginDto.getLogin();  // Utilisation du login envoyé par Postman
    String email = loginDto.getEmail();  // Utilisation de l'email envoyé par Angular

    // Utilisez l'email ou le login en fonction de ce qui est présent
    String identifier = (login != null) ? login : email;
    if (identifier == null) {
      throw new RuntimeException("Login or email must be provided");
    }

    String password = loginDto.getPassword();
    String token = authenticationService.loginAndGenerateToken(identifier, password);
    return ResponseEntity.ok(new AuthResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> createUser(@RequestBody Users users) {
    Users createdUser = usersService.createUser(users);
    // Créer un UserDto à partir de l'entité Users créée
    UserDto userDto = new UserDto(createdUser);
    // Retourner l'objet UserDto
    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }


  @GetMapping("/me")
  public ResponseEntity<UserDto> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = authentication.getName(); // Si le JWT contient le login
    Users currentUser = usersService.getUserByEmail(login); // Rechercher l'utilisateur par login

    // Créer un UserDto à partir de l'entité Users
    UserDto userDto = new UserDto(currentUser);

    return ResponseEntity.ok(userDto);
  }



}
