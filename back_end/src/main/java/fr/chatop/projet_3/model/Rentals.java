package fr.chatop.projet_3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rentals {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal surface;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private String picture;

  @Column(length = 2000)
  private String description;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private Users owner;

  @OneToMany(mappedBy = "rental")
  private List<Message> messages;
}
