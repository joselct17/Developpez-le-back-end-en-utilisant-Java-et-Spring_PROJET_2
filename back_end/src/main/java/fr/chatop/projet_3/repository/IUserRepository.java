package fr.chatop.projet_3.repository;

import fr.chatop.projet_3.model.Users;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepositoryImplementation<Users, Integer> {
  public Users findByEmail(String email);
}
