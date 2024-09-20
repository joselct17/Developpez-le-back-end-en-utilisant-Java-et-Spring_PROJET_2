package fr.chatop.projet_3.repository;

import fr.chatop.projet_3.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IMessagesRepository extends JpaRepository<Message, Integer> {

  // Rechercher par userId
  List<Message> findByUserId(Integer userId);

  // Rechercher par rentalId
  List<Message> findByRentalId(Integer rentalId);

  // Rechercher par userId et rentalId
  List<Message> findByUserIdAndRentalId(Integer userId, Integer rentalId);

}
