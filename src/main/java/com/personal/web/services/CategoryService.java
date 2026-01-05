package com.personal.web.services;

import com.personal.web.dtos.CategoryAndTagResponseDto;
import com.personal.web.dtos.CategoryRequestDto;
import com.personal.web.entities.Category;
import com.personal.web.exceptions.ConflictException;
import com.personal.web.exceptions.DuplicateException;
import com.personal.web.exceptions.ResourceNotFoundException;
import com.personal.web.mappers.CategoryAndTagMapper;
import com.personal.web.repositories.BlogRepository;
import com.personal.web.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryAndTagMapper categoryAndTagMapper;
    private final BlogRepository blogRepository;

    public CategoryAndTagResponseDto create(CategoryRequestDto requestDto){
        if(categoryRepository.existsByName(requestDto.name())){
            throw new DuplicateException("Category already exist");
        }
        Category category = new Category();
        category.setName(requestDto.name().toLowerCase());
        categoryRepository.save(category);

        return categoryAndTagMapper.toCategoryResponse(category);
    }

    public List<CategoryAndTagResponseDto> getAll(){
        List<Category> categories =  categoryRepository.findAll();
        return categories.stream()
                .map(categoryAndTagMapper::toCategoryResponse).toList();
    }

    public void delete(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));
        if(blogRepository.existsByCategory_Id(id)){
            throw new ConflictException("Category cannot be deleted because it is used by blogs");
        }

        categoryRepository.delete(category);
    }

    public CategoryAndTagResponseDto update(Long id, CategoryRequestDto requestDto){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));
        String newName = requestDto.name().toLowerCase();

        if(categoryRepository.existsByNameAndIdNot(newName, id)){
            throw new DuplicateException("Category name already exist");
        }

        category.setName(newName);
        categoryRepository.save(category);
        return categoryAndTagMapper.toCategoryResponse(category);
    }
}
