package com.firozkhan.server.dto.Response;

import com.firozkhan.server.enums.Role;

public record UserResponseDTO(
                String id,
                String username,
                String email,
                Role role) {

}
