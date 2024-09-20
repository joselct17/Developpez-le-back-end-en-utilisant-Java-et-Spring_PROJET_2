package fr.chatop.projet_3.model.dto;

import fr.chatop.projet_3.model.Rentals;
import fr.chatop.projet_3.model.Users;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RentalsDto {
  private Integer id;
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  private String description;
  private LocalDate created_at;
  private LocalDate updated_at;
  private Integer owner_id;

  public RentalsDto(Rentals rentals, Users owner) {
    this.id = rentals.getId();
    this.name = rentals.getName();
      this.surface = rentals.getSurface();
      this.price = rentals.getPrice();
      this.picture = rentals.getPicture();
      this.description = rentals.getDescription();
      this.created_at = rentals.getCreatedAt();
      this.updated_at = rentals.getUpdatedAt();

    // Assigner l'ID du propriétaire si l'utilisateur est bien le propriétaire
    if (owner != null) {
      this.owner_id = owner.getId();
    }
  }
}
