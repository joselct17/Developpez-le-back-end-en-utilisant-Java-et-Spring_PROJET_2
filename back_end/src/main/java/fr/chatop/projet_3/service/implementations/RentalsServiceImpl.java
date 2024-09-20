package fr.chatop.projet_3.service.implementations;

import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.RentalsDto;
import fr.chatop.projet_3.repository.IRentalsRepository;
import fr.chatop.projet_3.service.interfaces.IRentalsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentalsServiceImpl implements IRentalsService {

  private final IRentalsRepository rentalsRepository;

  @Override
  public List<Rentals> getAllRentals() {
    return rentalsRepository.findAll();
  }

  @Override
  public Optional<Rentals> getRentalById(Integer id) {
    return rentalsRepository.findById(id);
  }

  @Override
  public Rentals createRental(Rentals rental) {


    rental.setCreatedAt(LocalDate.now());
    return rentalsRepository.save(rental);
  }


  @Override
  public Rentals updateRental(Rentals rental) {
    rental.setUpdatedAt(LocalDate.now());  // Mettre à jour le champ "updatedAt"
    return rentalsRepository.save(rental);    // Sauvegarder les modifications
  }


    @Override
  public void deleteRental(Integer id) {
    rentalsRepository.deleteById(id);
  }
}
