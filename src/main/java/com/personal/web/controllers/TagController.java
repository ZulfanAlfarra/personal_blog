package com.personal.web.controllers;

import com.personal.web.dtos.CategoryAndTagResponseDto;
import com.personal.web.dtos.TagRequestDto;
import com.personal.web.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<CategoryAndTagResponseDto> createTag(@Valid @RequestBody TagRequestDto request){
        CategoryAndTagResponseDto tag = tagService.create(request);
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public ResponseEntity<List<CategoryAndTagResponseDto>> getAllTag(){
        List<CategoryAndTagResponseDto> tags= tagService.getAll();

        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id){
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryAndTagResponseDto> updateTag(@PathVariable Long id, @Valid @RequestBody TagRequestDto request){
        CategoryAndTagResponseDto tag = tagService.update(id, request);
        return ResponseEntity.ok(tag);
    }
}
