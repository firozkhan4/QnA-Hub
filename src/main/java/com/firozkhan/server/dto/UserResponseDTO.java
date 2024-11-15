package com.firozkhan.server.dto;

import com.firozkhan.server.enums.Role;

public record UserResponseDTO(
        String id,
        String username,
        String email,
        Role role) {

}
