package com.personal.web.mappers;

import com.personal.web.dtos.BlogResponseDto;
import com.personal.web.entities.Blog;
import com.personal.web.entities.Tag;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BlogMapper {

    public BlogResponseDto toDtoResponse(Blog blog){
        return new BlogResponseDto(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getStatus(),
                blog.getCategory().getName(),
                blog.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toSet()),
                blog.getCreated_at(),
                blog.getUpdated_at()
        );
    }
}
