package com.personal.web.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record SuccessResponse(
        List<?> data,
        LocalDateTime timestamp
) {
}
