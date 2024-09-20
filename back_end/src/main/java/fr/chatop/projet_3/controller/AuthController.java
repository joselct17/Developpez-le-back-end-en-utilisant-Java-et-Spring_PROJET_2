package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.config.AuthenticationService;
import fr.chatop.projet_3.model.AuthResponse;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.LoginDto;
import fr.chatop.projet_3.model.dto.UserDto;
import fr.chatop.projet_3.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


  /**
   * Authentifie un utilisateur et génère un token JWT.
   */
  @Operation(summary = "Authentifier un utilisateur", description = "Permet à un utilisateur de se connecter en utilisant son login ou son email et son mot de passe. Renvoie un token JWT.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Authentification réussie, token JWT renvoyé.",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = AuthResponse.class)) }),
    @ApiResponse(responseCode = "400", description = "Requête invalide."),
    @ApiResponse(responseCode = "401", description = "Authentification échouée, identifiants incorrects.")
  })
  @PostMapping("/login")
  public ResponseEntity<?> getToken(@RequestBody LoginDto loginDto) {
    String login = loginDto.getLogin();
    String email = loginDto.getEmail();

    // Utilisez l'email ou le login en fonction de ce qui est présent
    String identifier = (login != null) ? login : email;
    if (identifier == null) {
      throw new RuntimeException("Login or email must be provided");
    }

    String password = loginDto.getPassword();
    String token = authenticationService.loginAndGenerateToken(identifier, password);
    return ResponseEntity.ok(new AuthResponse(token));
  }


  /**
   * Crée un nouvel utilisateur.
   */
  @Operation(summary = "Créer un utilisateur", description = "Permet de créer un nouvel utilisateur dans le système.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès.",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = UserDto.class)) }),
    @ApiResponse(responseCode = "400", description = "Données invalides fournies.")
  })
  @PostMapping("/register")
  public ResponseEntity<UserDto> createUser(@RequestBody Users users) {
    Users createdUser = usersService.createUser(users);
    UserDto userDto = new UserDto(createdUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }



  /**
   * Récupère les informations de l'utilisateur actuellement authentifié.
   */
  @Operation(summary = "Récupérer l'utilisateur actuel", description = "Récupère les informations de l'utilisateur actuellement connecté.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Utilisateur actuel récupéré avec succès.",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = UserDto.class)) }),
    @ApiResponse(responseCode = "401", description = "Non authentifié, JWT manquant ou invalide.")
  })
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
