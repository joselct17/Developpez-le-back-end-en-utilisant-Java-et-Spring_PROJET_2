package fr.chatop.projet_3.repository;


import fr.chatop.projet_3.model.Rentals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRentalsRepository extends JpaRepository<Rentals, Integer> {
}
