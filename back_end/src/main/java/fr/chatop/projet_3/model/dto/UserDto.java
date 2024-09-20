package fr.chatop.projet_3.model.dto;

import fr.chatop.projet_3.model.Users;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDto {
  private int id;
  private String email;
  private String name;
  private LocalDate created_at;
  private LocalDate updated_at;

  public UserDto(Users users) {
    id = users.getId();
    email = users.getEmail();
    name = users.getName();
    created_at = users.getCreatedAt();
    updated_at = users.getUpdatedAt();
  }
}
