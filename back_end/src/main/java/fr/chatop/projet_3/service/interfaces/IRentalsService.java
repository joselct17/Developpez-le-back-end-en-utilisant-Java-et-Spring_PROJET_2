package fr.chatop.projet_3.service.interfaces;

import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.dto.RentalsDto;

import java.util.List;
import java.util.Optional;

public interface IRentalsService {

  List<Rentals> getAllRentals();

  Optional<Rentals> getRentalById(Integer id);

  Rentals createRental(Rentals rental);

  void deleteRental(Integer id);

  Rentals updateRental( Rentals rentals);

}
