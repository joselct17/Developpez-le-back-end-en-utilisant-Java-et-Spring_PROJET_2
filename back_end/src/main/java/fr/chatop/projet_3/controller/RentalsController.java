package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.RentalsDto;
import fr.chatop.projet_3.service.interfaces.IRentalsService;
import fr.chatop.projet_3.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rentals")
public class RentalsController {

  private final IRentalsService rentalService;
  private final IUserService usersService;

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
  public ResponseEntity<Rentals> createRental(
    @RequestParam("name") String name,
    @RequestParam("surface") BigDecimal surface,
    @RequestParam("price") BigDecimal price,
    @RequestParam("description") String description,
    @RequestParam("picture") MultipartFile picture) throws IOException {

    // Créer une nouvelle instance de Rentals
    Rentals rental = new Rentals();
    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);

    // Récupération de l'utilisateur courant
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    Users currentUser = usersService.getUserByEmail(email);

    // Ajouter la location à la liste des locations de l'utilisateur
    List<Rentals> rentals = currentUser.getRentals();
    rentals.add(rental);
    currentUser.setRentals(rentals);

    // Sauvegarder le fichier de l'image si non vide
    if (!picture.isEmpty()) {
      String fileName = UUID.randomUUID().toString() + "_" + picture.getOriginalFilename();
      String uploadDir = "uploads";
      Path uploadPath = Paths.get(uploadDir);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      Path filePath = uploadPath.resolve(fileName);
      Files.copy(picture.getInputStream(), filePath);
      rental.setPicture(filePath.toString());
    } else {
      throw new RuntimeException("Picture file is empty");
    }

    // Sauvegarder la location et mettre à jour l'utilisateur
    rentalService.createRental(rental);
    usersService.updateRentalsUser(currentUser); // Met à jour l'utilisateur avec la nouvelle location

    return ResponseEntity.status(HttpStatus.CREATED).body(rental);
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
