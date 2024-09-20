package fr.chatop.projet_3.repository;

import fr.chatop.projet_3.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepositoryImplementation<Users, Integer> {
  public Users findByEmail(String email);

  // Requête personnalisée pour trouver l'utilisateur qui possède une location avec un certain rentalId
  @Query("SELECT u FROM Users u JOIN u.rentals r WHERE r.id = :rentalId")
  Users findOwnerByRentalId(@Param("rentalId") Integer rentalId);
}
