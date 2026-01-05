package com.personal.web.dtos;

import com.personal.web.entities.BlogStatus;
import com.personal.web.entities.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record BlogRequestDto(
        @NotBlank(message = "Title is required")
        @Size(min = 5, message = "Title at least 5 character")
        String title,

        @NotBlank(message = "Must have content")
        String content,

        @NotNull(message = "Status is required")
        BlogStatus status,

        @NotNull(message = "Category is required")
        Long category_id,

        @NotEmpty(message = "Tags is required")
        Set<Long> tag_ids
) {
}
