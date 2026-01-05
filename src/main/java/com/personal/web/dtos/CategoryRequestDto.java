package com.personal.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank(message = "Category name is required")
        @Size(min = 3, message = "At least 2 Character")
        String name
) {
}
