package com.personal.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank(message = "Username is required")
        @Size(min = 3, message = "Username at least 3 character")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password min 8 character")
        String password
) {
}
