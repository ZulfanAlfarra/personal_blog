package com.personal.web.mappers;

import com.personal.web.dtos.CategoryAndTagResponseDto;
import com.personal.web.entities.Category;
import com.personal.web.entities.Tag;
import org.springframework.stereotype.Component;

@Component
public class CategoryAndTagMapper {
    public CategoryAndTagResponseDto toCategoryResponse(Category category){
        return new CategoryAndTagResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public CategoryAndTagResponseDto toTagResponse(Tag tag){
        return new CategoryAndTagResponseDto(
                tag.getId(),
                tag.getName()
        );
    }
}
