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

  @JsonCreator
  public MessageDto(@JsonProperty("message") String message,
                    @JsonProperty("user_id") Integer userId,
                    @JsonProperty("rental_id") Integer rentalId) {
    this.message = message;
    this.userId = userId;
    this.rentalId = rentalId;
  }

}
