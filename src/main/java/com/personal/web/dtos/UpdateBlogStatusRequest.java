package com.personal.web.dtos;

import com.personal.web.entities.BlogStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateBlogStatusRequest(
        @NotNull(message = "Status is required")
        BlogStatus status
) {
}
