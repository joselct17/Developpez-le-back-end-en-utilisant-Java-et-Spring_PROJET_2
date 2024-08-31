package fr.chatop.projet_3.repository;

import fr.chatop.projet_3.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IMessagesRepository extends JpaRepository<Message, Integer> {
}
