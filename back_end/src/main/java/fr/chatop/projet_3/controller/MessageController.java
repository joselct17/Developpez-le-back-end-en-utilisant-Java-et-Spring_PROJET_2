package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.model.Message;
import fr.chatop.projet_3.model.dto.MessageDto;
import fr.chatop.projet_3.service.interfaces.IMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

  private final IMessageService messageService;


  @GetMapping
  public ResponseEntity<List<Message>> getAllMessages() {
    List<Message> messages = messageService.getAllMessages();
    return ResponseEntity.ok(messages);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
    Optional<Message> messageOpt = messageService.getMessageById(id);
    if (messageOpt.isPresent()) {
      return ResponseEntity.ok(messageOpt.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


  @PostMapping
  public ResponseEntity<Message> createMessage(@RequestBody MessageDto messageDto) {
    Integer userId = messageDto.getUserId();
    Integer rentalId = messageDto.getRentalId();
    Message createdMessage = messageService.createMessage(messageDto, userId, rentalId);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
  }


  @PutMapping("/{id}")
  public ResponseEntity<Message> updateMessage(@PathVariable Integer id, @RequestBody MessageDto messageDto) {
    Message updatedMessage = messageService.updateMessage(id, messageDto);
    if (updatedMessage != null) {
      return ResponseEntity.ok(updatedMessage);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
    messageService.deleteMessage(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
