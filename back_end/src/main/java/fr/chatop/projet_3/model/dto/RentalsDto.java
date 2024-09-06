package fr.chatop.projet_3.model.dto;

import fr.chatop.projet_3.model.Rentals;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RentalsDto {

  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  private String description;

  public RentalsDto(Rentals rentals) {
    this.name = rentals.getName();
      this.surface = rentals.getSurface();
      this.price = rentals.getPrice();
      this.picture = rentals.getPicture();
      this.description = rentals.getDescription();
  }
}
