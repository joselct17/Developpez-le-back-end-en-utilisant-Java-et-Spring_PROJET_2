package com.openclassrooms.api.services;

import com.openclassrooms.api.dtos.UserDTO;
import com.openclassrooms.api.entities.User;
import com.openclassrooms.api.exceptions.DuplicationException;
import com.openclassrooms.api.exceptions.NotFoundException;
import com.openclassrooms.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Person not found: " + id));
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found: " + email));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(User user) {
        checkEmailDuplication(user);
        return repository.save(user);
    }

    public UserDTO create(UserDTO dto) {
        return new UserDTO(create(new User(dto)));
    }


    private void checkEmailDuplication(User user) {
        final String email = user.getEmail();
        if (email != null && email.length() > 0) {
            final Integer id = user.getId();
            final User u = repository.findByEmail(email).orElse(null);
            if (u != null && Objects.equals(u.getEmail(), email) && !Objects.equals(u.getId(), id)) {
                throw new DuplicationException("Email duplication: " + email);
            }
        }
    }

}
