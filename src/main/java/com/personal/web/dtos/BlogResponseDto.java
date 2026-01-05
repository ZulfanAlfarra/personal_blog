package com.personal.web.dtos;

import com.personal.web.entities.BlogStatus;
import com.personal.web.entities.Category;
import com.personal.web.entities.Tag;

import java.time.LocalDateTime;
import java.util.Set;

public record BlogResponseDto(
        Long id,
        String title,
        String content,
        BlogStatus status,
        String category,
        Set<String> tags,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
