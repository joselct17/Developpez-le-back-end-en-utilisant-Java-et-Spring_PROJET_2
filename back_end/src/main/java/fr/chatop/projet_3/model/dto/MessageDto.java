package fr.chatop.projet_3.model.dto;

import fr.chatop.projet_3.model.Message;
import lombok.Data;

@Data
public class MessageDto {
  private String message;

  public MessageDto(Message message) {
    this.message = message.getMessage();
  }
}
