package fr.chatop.projet_3.model.dto;

import fr.chatop.projet_3.model.Rentals;
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

  public RentalsDto(Rentals rentals) {
    this.id = rentals.getId();
    this.name = rentals.getName();
      this.surface = rentals.getSurface();
      this.price = rentals.getPrice();
      this.picture = rentals.getPicture();
      this.description = rentals.getDescription();
      this.created_at = rentals.getCreatedAt();
      this.updated_at = rentals.getUpdatedAt();
  }
}
