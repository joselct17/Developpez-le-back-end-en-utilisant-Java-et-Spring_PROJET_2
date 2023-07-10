package com.openclassrooms.api.controllers;

import com.openclassrooms.api.dtos.GetUserResponseDTO;
import com.openclassrooms.api.entities.User;
import com.openclassrooms.api.services.JwtService;
import com.openclassrooms.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponseDTO> findById(@PathVariable Integer id) {
        final User user = userService.findById(id);
        return ResponseEntity.ok(new GetUserResponseDTO(user));
    }
}
