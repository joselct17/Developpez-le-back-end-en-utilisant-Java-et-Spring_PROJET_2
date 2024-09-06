package fr.chatop.projet_3.service.implementations;

import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.dto.RentalsDto;
import fr.chatop.projet_3.repository.IRentalsRepository;
import fr.chatop.projet_3.service.interfaces.IRentalsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    rental.setCreatedAt(LocalDateTime.now());
    return rentalsRepository.save(rental);
  }


  @Override
  public Rentals updateRental(Integer id, RentalsDto rentalDto) {
    Optional<Rentals> existingRentalOpt = rentalsRepository.findById(id);
    if (existingRentalOpt.isPresent()) {
      Rentals existingRental = existingRentalOpt.get();
      existingRental.setName(rentalDto.getName());
      existingRental.setSurface(rentalDto.getSurface());
      existingRental.setPrice(rentalDto.getPrice());
      existingRental.setPicture(rentalDto.getPicture());
      existingRental.setDescription(rentalDto.getDescription());
      existingRental.setUpdatedAt(LocalDateTime.now());
      return rentalsRepository.save(existingRental);
    }
    return null;
  }

    @Override
  public void deleteRental(Integer id) {
    rentalsRepository.deleteById(id);
  }
}
