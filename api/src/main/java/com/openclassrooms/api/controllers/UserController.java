package com.openclassrooms.api.controllers;

import com.mysql.cj.log.Log;
import com.openclassrooms.api.dtos.*;
import com.openclassrooms.api.entities.User;
import com.openclassrooms.api.services.JwtService;
import com.openclassrooms.api.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/me")
    public MeResponseDTO me(@RequestHeader String authorization) {
        final String token = authorization.substring(7);
        final String mail = jwtService.getClaims(token).getSubject();
        final User user = userService.findByEmail(mail);
        return new MeResponseDTO(user);
    }
}
