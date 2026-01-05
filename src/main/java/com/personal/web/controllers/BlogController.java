package com.personal.web.controllers;

import com.personal.web.dtos.BlogRequestDto;
import com.personal.web.dtos.BlogResponseDto;
import com.personal.web.dtos.UpdateBlogStatusRequest;
import com.personal.web.entities.BlogStatus;
import com.personal.web.services.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponseDto> createBlog(@Valid @RequestBody BlogRequestDto request){
        BlogResponseDto blog = blogService.createBlog(request);
        return ResponseEntity.ok(blog);
    }

    @GetMapping
    public ResponseEntity<List<BlogResponseDto>> getAllBlog(){
        List<BlogResponseDto> blogs = blogService.getAll();
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseDto> getBlogById(@PathVariable Long id){
        BlogResponseDto blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogById(@PathVariable Long id){
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponseDto> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogRequestDto requestDto){
        BlogResponseDto blog = blogService.updateBlog(id, requestDto);
        return ResponseEntity.ok(blog);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BlogResponseDto> updateBlogStatus(@PathVariable Long id, @Valid @RequestBody UpdateBlogStatusRequest request){
        BlogResponseDto blog = blogService.updateBlogStatus(id, request.status());
        return ResponseEntity.ok(blog);
    }
}
