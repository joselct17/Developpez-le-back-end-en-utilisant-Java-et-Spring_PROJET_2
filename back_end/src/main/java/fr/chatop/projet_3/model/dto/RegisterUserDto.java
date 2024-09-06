package fr.chatop.projet_3.model.dto;

import fr.chatop.projet_3.model.Users;
import lombok.Data;

@Data
public class RegisterUserDto {
  private String name;
  private String email;
  private char[] password;

  public RegisterUserDto(Users users) {
    this.name = users.getName();
    this.email = users.getEmail();
    this.password = users.getPassword();
  }
}
