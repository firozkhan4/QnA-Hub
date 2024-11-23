package com.firozkhan.server.dto.Response;

public class AuthResponseDTO {

    private UserResponseDTO user;
    private String token;

    public AuthResponseDTO(UserResponseDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

}
