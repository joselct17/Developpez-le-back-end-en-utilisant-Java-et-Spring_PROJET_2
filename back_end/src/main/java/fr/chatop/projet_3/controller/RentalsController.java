package fr.chatop.projet_3.controller;


import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.Users;
import fr.chatop.projet_3.model.dto.RentalsDto;
import fr.chatop.projet_3.service.interfaces.IRentalsService;
import fr.chatop.projet_3.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rentals")
public class RentalsController {

  private final IRentalsService rentalService;
  private final IUserService usersService;

  /**
   * Récupère la liste de toutes les locations
   */
  @Operation(summary = "Récupérer toutes les locations")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Liste des locations récupérée",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Rentals.class)) })
  })
  @GetMapping
  public ResponseEntity<Map<String, List<RentalsDto>>> getAllRentals() {
    List<Rentals> rentals = rentalService.getAllRentals();
    // Convertir chaque entité Rentals en RentalsDto
    List<RentalsDto> rentalsDtoList = rentals.stream()
      .map(RentalsDto::new)
      .toList();
    Map<String, List<RentalsDto>> response = new HashMap<>();
    response.put("rentals", rentalsDtoList);

    return ResponseEntity.ok(response);
  }

  /**
   * Récupère une location par son identifiant
   */
  @Operation(summary = "Récupérer une location par ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Location trouvée",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Rentals.class)) }),
    @ApiResponse(responseCode = "404", description = "Location non trouvée")
  })
  @GetMapping("/{id}")
  public ResponseEntity<RentalsDto> getRentalById(@PathVariable Integer id) {
    Optional<Rentals> rentalOpt = rentalService.getRentalById(id);
    if (rentalOpt.isPresent()) {
      RentalsDto rentalDto = new RentalsDto(rentalOpt.get());
      return ResponseEntity.ok(rentalDto);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }


  /**
   * Crée une nouvelle location
   */
  @Operation(summary = "Créer une nouvelle location")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Location créée avec succès",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Rentals.class)) }),
    @ApiResponse(responseCode = "400", description = "Erreur dans les données envoyées")
  })
  @PostMapping
  public ResponseEntity<RentalsDto> createRental(
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

    List<Rentals> rentals = currentUser.getRentals();
    rentals.add(rental);
    currentUser.setRentals(rentals);

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
      throw new RuntimeException("Le fichier d'image est vide");
    }

    rentalService.createRental(rental);
    usersService.updateRentalsUser(currentUser);

    // Retourner un RentalsDto après la création
    RentalsDto rentalDto = new RentalsDto(rental);
    return ResponseEntity.status(HttpStatus.CREATED).body(rentalDto);
  }


  @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
  public ResponseEntity<RentalsDto> updateRental(
    @PathVariable Integer id,
    @RequestParam("name") String name,
    @RequestParam("surface") BigDecimal surface,
    @RequestParam("price") BigDecimal price,
    @RequestParam("description") String description,
    @RequestParam(value = "picture", required = false) MultipartFile picture) throws IOException {

    // Récupérer la location à partir de l'ID
    Rentals rental = rentalService.getRentalById(id)
      .orElseThrow(() -> new RuntimeException("Rental not found"));

    // Mettre à jour les informations du Rentals avec les nouvelles valeurs
    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);

    // Si une image est fournie, mettre à jour le fichier image
    if (picture != null && !picture.isEmpty()) {
      String fileName = UUID.randomUUID().toString() + "_" + picture.getOriginalFilename();
      String uploadDir = "uploads";
      Path uploadPath = Paths.get(uploadDir);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      Path filePath = uploadPath.resolve(fileName);
      Files.copy(picture.getInputStream(), filePath);
      rental.setPicture(filePath.toString());
    }

    // Sauvegarder les modifications via le service
    Rentals updatedRental = rentalService.updateRental(rental);  // Utiliser directement Rentals

    // Convertir l'entité Rentals mise à jour en RentalsDto
    RentalsDto updatedRentalDto = new RentalsDto(updatedRental);

    // Retourner le RentalsDto
    return ResponseEntity.ok(updatedRentalDto);
  }




  /**
   * Supprime une location par ID
   */
  @Operation(summary = "Supprimer une location")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Location supprimée avec succès"),
    @ApiResponse(responseCode = "404", description = "Location non trouvée")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
    rentalService.deleteRental(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
