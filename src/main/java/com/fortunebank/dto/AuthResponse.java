package com.fortunebank.dto;

public class AuthResponse {

    private String token;
    private boolean isAdmin;  // ✅ whether the user is an admin or not

    // ✅ Default constructor (needed by Jackson)
    public AuthResponse() {
    }

    // ✅ Constructor with both fields
    public AuthResponse(String token, boolean isAdmin) {
        this.token = token;
        this.isAdmin = isAdmin;
    }

    // ✅ Constructor (token-only)
    public AuthResponse(String token) {
        this.token = token;
        this.isAdmin = false;
    }

    // ✅ Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }
}
