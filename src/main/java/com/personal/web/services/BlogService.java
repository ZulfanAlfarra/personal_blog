package com.personal.web.services;

import com.personal.web.dtos.BlogRequestDto;
import com.personal.web.dtos.BlogResponseDto;
import com.personal.web.entities.Blog;
import com.personal.web.entities.BlogStatus;
import com.personal.web.entities.Category;
import com.personal.web.entities.Tag;
import com.personal.web.exceptions.ConflictException;
import com.personal.web.exceptions.ResourceNotFoundException;
import com.personal.web.mappers.BlogMapper;
import com.personal.web.repositories.BlogRepository;
import com.personal.web.repositories.CategoryRepository;
import com.personal.web.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@PreAuthorize("authentication.name == 'zulfan'")
public class BlogService {
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final BlogMapper blogMapper;

    private Category getCategoryOrThrow(Long category_id){
        return categoryRepository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + category_id + " does not exist"));
    }

    private Set<Tag> getTagsOrThrow(Set<Long> tag_ids){
        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(tag_ids));
        if(tags.size() != tag_ids.size()){
            throw new ResourceNotFoundException("One or more tags do not exist");
        }
        return tags;
    }


    public BlogResponseDto createBlog(BlogRequestDto requestDto){
        Category category = getCategoryOrThrow(requestDto.category_id());

        Set<Tag> tags = getTagsOrThrow(requestDto.tag_ids());


        Blog blog = new Blog();
        blog.setTitle(requestDto.title());
        blog.setContent(requestDto.content());
        blog.setStatus(requestDto.status());
        blog.setCategory(category);
        blog.setTags(tags);

        blogRepository.save(blog);
        return blogMapper.toDtoResponse(blog);
    }

    public List<BlogResponseDto> getAll(){
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(blogMapper::toDtoResponse)
                .toList();
    }

    public BlogResponseDto getBlogById(Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Blog not found with id " + id));
        return blogMapper.toDtoResponse(blog);
    }

    public void delete(Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Blog not found with id" + id));
        blogRepository.delete(blog);
    }

    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto){
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found with id " + id));
        if(blog.getStatus() == BlogStatus.ARCHIVED){
            throw  new ConflictException("Archived blog cannot be modified");
        }

        Category category = getCategoryOrThrow(requestDto.category_id());
        Set<Tag> tags = getTagsOrThrow(requestDto.tag_ids());

        blog.setTitle(requestDto.title());
        blog.setContent(requestDto.content());
        blog.setStatus(requestDto.status());
        blog.setCategory(category);
        blog.setTags(tags);

        blogRepository.save(blog);
        return blogMapper.toDtoResponse(blog);
    }

    public BlogResponseDto updateBlogStatus(Long id, BlogStatus status){
        Blog blog = blogRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("Blog not found with id " + id)));

        blog.setStatus(status);
        blogRepository.save(blog);
        return blogMapper.toDtoResponse(blog);
    }
}
