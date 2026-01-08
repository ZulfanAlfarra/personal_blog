package com.personal.web.controllers;

import com.personal.web.dtos.BlogRequestDto;
import com.personal.web.dtos.BlogResponseDto;
import com.personal.web.dtos.SuccessResponse;
import com.personal.web.dtos.UpdateBlogStatusRequest;
import com.personal.web.entities.BlogStatus;
import com.personal.web.services.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createBlog(@Valid @RequestBody BlogRequestDto request){
        BlogResponseDto blog = blogService.createBlog(request);
        return ResponseEntity.ok(
                new SuccessResponse(List.of(blog), LocalDateTime.now())
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllBlog(){
        List<BlogResponseDto> blogs = blogService.getAll();
        return ResponseEntity.ok(
                new SuccessResponse(blogs, LocalDateTime.now())
        );
    }

    @GetMapping("/published")
    public ResponseEntity<SuccessResponse> getBlogPublished(Pageable pageable){
        List<BlogResponseDto> blogs = blogService.getBlogByStatus(BlogStatus.PUBLISHED, pageable);
        return ResponseEntity.ok(
                new SuccessResponse(blogs, LocalDateTime.now())
        );
    }

    @GetMapping("/archived")
    public ResponseEntity<SuccessResponse> getBlogArchived(Pageable pageable){
        List<BlogResponseDto> blogs = blogService.getBlogByStatus(BlogStatus.ARCHIVED, pageable);
        return ResponseEntity.ok(
                new SuccessResponse(blogs, LocalDateTime.now())
        );
    }

    @GetMapping("/draft")
    public ResponseEntity<SuccessResponse> getBlogDraft(Pageable pageable){
        List<BlogResponseDto> blogs = blogService.getBlogByStatus(BlogStatus.DRAFT, pageable);
        return ResponseEntity.ok(
                new SuccessResponse(blogs, LocalDateTime.now())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getBlogById(@PathVariable Long id){
        BlogResponseDto blog = blogService.getBlogById(id);
        return ResponseEntity.ok(
                new SuccessResponse(List.of(blog), LocalDateTime.now())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogById(@PathVariable Long id){
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogRequestDto requestDto){
        BlogResponseDto blog = blogService.updateBlog(id, requestDto);
        return ResponseEntity.ok(
                new SuccessResponse(List.of(blog), LocalDateTime.now())
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateBlogStatus(@PathVariable Long id, @Valid @RequestBody UpdateBlogStatusRequest request){
        BlogResponseDto blog = blogService.updateBlogStatus(id, request.status());
        return ResponseEntity.ok(
                new SuccessResponse(List.of(blog), LocalDateTime.now())
        );
    }
}
