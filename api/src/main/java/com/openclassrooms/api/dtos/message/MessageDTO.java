package com.openclassrooms.api.dtos.message;


import com.openclassrooms.api.entities.Rental;
import com.openclassrooms.api.entities.User;
import jakarta.persistence.ManyToOne;

public class MessageDTO {

    private String message;

    private User user;

    private Rental rental;

    public MessageDTO(String message, User user, Rental rental) {
        this.message = message;
        this.user = user;
        this.rental = rental;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
}
