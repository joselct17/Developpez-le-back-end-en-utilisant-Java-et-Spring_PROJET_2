package com.openclassrooms.api.repositories;

import com.openclassrooms.api.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
