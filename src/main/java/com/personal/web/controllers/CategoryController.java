package com.personal.web.controllers;

import com.personal.web.dtos.BlogRequestDto;
import com.personal.web.dtos.CategoryAndTagResponseDto;
import com.personal.web.dtos.CategoryRequestDto;
import com.personal.web.entities.Category;
import com.personal.web.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryAndTagResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto request){
        CategoryAndTagResponseDto category = categoryService.create(request);

        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryAndTagResponseDto>> getAllCategory(){
        List<CategoryAndTagResponseDto> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryAndTagResponseDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDto request){
        CategoryAndTagResponseDto category = categoryService.update(id, request);
        return ResponseEntity.ok(category);
    }
}
