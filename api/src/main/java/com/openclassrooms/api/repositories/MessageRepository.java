package com.openclassrooms.api.repositories;

import com.openclassrooms.api.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
