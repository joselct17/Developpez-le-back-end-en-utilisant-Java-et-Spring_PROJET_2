package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.dto.RentalsDto;
import fr.chatop.projet_3.service.interfaces.IRentalsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/rentals")
public class RentalsController {

  private final IRentalsService rentalService;

  @GetMapping
  public ResponseEntity<List<Rentals>> getAllRentals() {
    List<Rentals> rentals = rentalService.getAllRentals();
    return ResponseEntity.ok(rentals);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Rentals> getRentalById(@PathVariable Integer id) {
    Optional<Rentals> rentalOpt = rentalService.getRentalById(id);
    if (rentalOpt.isPresent()) {
      return ResponseEntity.ok(rentalOpt.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping
  public ResponseEntity<Rentals> createRental(@RequestBody Rentals rental) {
    Rentals createdRental = rentalService.createRental(rental);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdRental);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Rentals> updateRental(@PathVariable Integer id, @RequestBody RentalsDto rentalDto) {
    Rentals updatedRental = rentalService.updateRental(id, rentalDto);
    if (updatedRental != null) {
      return ResponseEntity.ok(updatedRental);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
    rentalService.deleteRental(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
