package fr.chatop.projet_3.service.implementations;

import fr.chatop.projet_3.model.Message;
import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.MessageDto;
import fr.chatop.projet_3.repository.IMessagesRepository;
import fr.chatop.projet_3.repository.IRentalsRepository;
import fr.chatop.projet_3.repository.IUserRepository;
import fr.chatop.projet_3.service.interfaces.IMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements IMessageService {

  private final IMessagesRepository messageRepository;
  private final IUserRepository userRepository;
  private final IRentalsRepository rentalsRepository;

  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  @Override
  public List<Message> getMessages(Integer userId, Integer rentalId) {
    // Filtrer les messages en fonction de ce qui est fourni
    if (userId != null && rentalId != null) {
      // Filtrer par userId et rentalId
      return messageRepository.findByUserIdAndRentalId(userId, rentalId);
    } else if (userId != null) {
      // Filtrer par userId
      return messageRepository.findByUserId(userId);
    } else if (rentalId != null) {
      // Filtrer par rentalId
      return messageRepository.findByRentalId(rentalId);
    } else {
      // Si aucun filtre n'est fourni, renvoyer tous les messages
      return messageRepository.findAll();
    }
  }

  public Optional<Message> getMessageById(Integer id) {
    return messageRepository.findById(id);
  }

  public Message createMessage(MessageDto messageDto, Integer userId, Integer rentalId) {
    // Vérifie que userId et rentalId ne sont pas nuls
    if (userId == null || rentalId == null) {
      throw new IllegalArgumentException("User ID and Rental ID must not be null");
    }

    Users user = userRepository.findById(userId)
      .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

    Rentals rental = rentalsRepository.findById(rentalId)
      .orElseThrow(() -> new IllegalArgumentException("Invalid rental ID"));

    Message message = new Message();
    message.setMessage(messageDto.getMessage());


    message.setUser(user);
    message.setRental(rental);
    message.setCreatedAt(LocalDateTime.now());

    return messageRepository.save(message);
  }


  public Message updateMessage(Integer id, MessageDto messageDto) {
    Optional<Message> existingMessageOpt = messageRepository.findById(id);
    if (existingMessageOpt.isPresent()) {
      Message existingMessage = existingMessageOpt.get();
      existingMessage.setMessage(messageDto.getMessage());
      existingMessage.setUpdatedAt(LocalDateTime.now());
      return messageRepository.save(existingMessage);
    }
    return null;
  }

  public void deleteMessage(Integer id) {
    messageRepository.deleteById(id);
  }
}

