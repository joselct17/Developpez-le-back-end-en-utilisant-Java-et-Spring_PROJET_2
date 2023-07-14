package com.openclassrooms.api.services;


import com.openclassrooms.api.dtos.message.MessageDTO;
import com.openclassrooms.api.entities.Message;
import com.openclassrooms.api.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public Message create(MessageDTO dto) {
        Message message = new Message(dto);
        repository.save(message);
        return message;
    }

}
