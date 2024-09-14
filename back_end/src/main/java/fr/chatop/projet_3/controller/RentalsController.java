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

    Rentals rental = new Rentals();
    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    Users currentUser = usersService.getUserByEmail(email);

    rental.setOwner(currentUser);


    if (!picture.isEmpty()) {
      // Sauvegarder le fichier sur le disque
      String fileName = picture.getOriginalFilename();
      String uploadDir = "uploads"; // Chemin relatif ou absolu où les fichiers sont stockés

      // Créer le répertoire si nécessaire
      Path uploadPath = Paths.get(uploadDir);
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      // Sauvegarder le fichier
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(picture.getInputStream(), filePath);

      // Mettre à jour l'entité Rentals avec le chemin de l'image
      rental.setPicture(filePath.toString());
    } else {
      throw new RuntimeException("Picture file is empty");
    }

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
