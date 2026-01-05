package com.personal.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagRequestDto(
        @NotBlank(message = "Tag name is required")
        @Size(min = 3, message = "At least 3 character")
        String name
) {
}
