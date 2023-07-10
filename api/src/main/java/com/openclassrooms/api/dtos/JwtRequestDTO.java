package com.openclassrooms.api.dtos;

public class JwtRequestDTO {
    private String authorization;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}