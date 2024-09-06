package fr.chatop.projet_3.service.implementations;

import fr.chatop.projet_3.model.Message;
import fr.chatop.projet_3.model.dto.MessageDto;
import fr.chatop.projet_3.repository.IMessagesRepository;
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

  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  public Optional<Message> getMessageById(Integer id) {
    return messageRepository.findById(id);
  }

  public Message createMessage(MessageDto messageDto, Integer userId, Integer rentalId) {
    Message message = new Message();
    message.setMessage(messageDto.getMessage());
    message.setCreatedAt(LocalDateTime.now());
    // Assign user and rental (fetch from DB using userId and rentalId)
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

