package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.config.JWTService;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.RegisterUserDto;
import fr.chatop.projet_3.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UsersController {

  private final IUserService usersService;

  private final JWTService jwtService;


  @PostMapping("/login")
  public String getToken(Authentication authentication) {
    String token = jwtService.generateToken(authentication);
    return token;
  }

  @PostMapping("/register")
  public ResponseEntity<Users> createUser(Users users) {
    Users createdUser = usersService.createUser(users);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }



  @GetMapping
  public ResponseEntity<List<Users>> getAllUsers() {
    List<Users> users = usersService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
    Optional<Users> userOpt = usersService.getUserById(id);
    if (userOpt.isPresent()) {
      return ResponseEntity.ok(userOpt.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }



  @PutMapping("/{id}")
  public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody RegisterUserDto registerUserDto) {
    Users updatedUser = usersService.updateUser(id, registerUserDto);
    if (updatedUser != null) {
      return ResponseEntity.ok(updatedUser);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
    usersService.deleteUser(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
