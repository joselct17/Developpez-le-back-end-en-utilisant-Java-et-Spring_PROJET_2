package fr.chatop.projet_3.service.interfaces;

import fr.chatop.projet_3.model.Message;
import fr.chatop.projet_3.model.dto.MessageDto;

import java.util.List;
import java.util.Optional;

 public interface IMessageService {


   List<Message> getAllMessages();

   List<Message> getMessages(Integer userId, Integer rentalId);

   Message createMessage(MessageDto messageDto, Integer userId, Integer rentalId);

   Message updateMessage(Integer id, MessageDto messageDto);

   void deleteMessage(Integer id);

}
