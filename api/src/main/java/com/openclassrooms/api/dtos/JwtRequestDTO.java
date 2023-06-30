package com.openclassrooms.api.dtos;

public class JwtRequestDTO {
    private String authorzation;

    public String getAuthorzation() {
        return authorzation;
    }

    public void setAuthorzation(String authorzation) {
        this.authorzation = authorzation;
    }
}