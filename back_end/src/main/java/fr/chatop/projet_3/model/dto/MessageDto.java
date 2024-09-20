package fr.chatop.projet_3.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.chatop.projet_3.model.Message;
import lombok.Data;

@Data
public class MessageDto {
  private String message;
  private Integer userId;
  private Integer rentalId;

  // Constructeur pour l'utilisation avec @JsonCreator
  @JsonCreator
  public MessageDto(@JsonProperty("message") String message,
                    @JsonProperty("user_id") Integer userId,
                    @JsonProperty("rental_id") Integer rentalId) {
    this.message = message;
    this.userId = userId;
    this.rentalId = rentalId;
  }

  // Nouveau constructeur qui accepte un objet Message
  public MessageDto(Message message) {
    this.message = message.getMessage();
    this.userId = message.getUser().getId(); // Assurez-vous que getUser() retourne l'utilisateur
    this.rentalId = message.getRental().getId(); // Assurez-vous que getRental() retourne la location
  }
}
