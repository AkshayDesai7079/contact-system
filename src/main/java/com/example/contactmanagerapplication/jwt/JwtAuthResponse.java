package com.example.contactmanagerapplication.jwt;

public class JwtAuthResponse {
    private String jwtToken;

    public JwtAuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
