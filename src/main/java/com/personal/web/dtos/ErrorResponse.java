package com.personal.web.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ErrorResponse(
        int status,
        Map<String, List<String>> errors,
        LocalDateTime timestamp
) {
}
