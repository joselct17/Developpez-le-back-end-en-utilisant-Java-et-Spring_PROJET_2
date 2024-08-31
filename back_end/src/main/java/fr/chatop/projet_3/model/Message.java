package fr.chatop.projet_3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGES")
@Getter
@Setter
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 2000, nullable = false)
  private String message;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "rental_id", nullable = false)
  private Rentals rental;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Users user;


}
