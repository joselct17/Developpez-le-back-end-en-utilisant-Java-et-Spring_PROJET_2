package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.model.Message;
import fr.chatop.projet_3.model.dto.MessageDto;
import fr.chatop.projet_3.service.interfaces.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

  private final IMessageService messageService;

  /**
   * Récupère la liste de tous les messages, filtrée par `userId` et/ou `rentalId` si fourni.
   */
  @Operation(summary = "Récupérer tous les messages", description = "Récupère la liste de tous les messages, avec la possibilité de filtrer par userId et/ou rentalId.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Liste des messages récupérée",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = MessageDto.class)) }),
    @ApiResponse(responseCode = "400", description = "Paramètres de requête invalides",
        content = { @Content(mediaType = "application/json",
        schema = @Schema(example = "{ \"error\": \"Paramètres de requête invalides\" }"))})
          })
  @GetMapping
  public ResponseEntity<List<MessageDto>> getAllMessages(
    @RequestParam(value = "userId", required = false) Integer userId,
    @RequestParam(value = "rentalId", required = false) Integer rentalId) {

    List<Message> messages;

    // Si aucun filtre n'est fourni, renvoyer tous les messages
    if (userId == null && rentalId == null) {
      messages = messageService.getAllMessages();
    } else {
      // Récupérer les messages basés sur les paramètres fournis (userId, rentalId, ou les deux)
      messages = messageService.getMessages(userId, rentalId);
    }

    // Convertir en DTOs
    List<MessageDto> messageDtos = messages.stream()
      .map(MessageDto::new)  // Utilisation du constructeur qui accepte un Message
      .collect(Collectors.toList());

    return ResponseEntity.ok(messageDtos);
  }


  /**
   * Crée un nouveau message
   */
  @Operation(summary = "Créer un nouveau message", description = "Permet de créer un nouveau message en fournissant les informations requises dans un MessageDto.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Message créé avec succès",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Message.class)) }),
    @ApiResponse(responseCode = "400", description = "Données invalides fournies")
  })
  @PostMapping
  public ResponseEntity<Message> createMessage(@RequestBody MessageDto messageDto) {
    Integer userId = messageDto.getUserId();
    Integer rentalId = messageDto.getRentalId();
    Message createdMessage = messageService.createMessage(messageDto, userId, rentalId);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
  }

}
