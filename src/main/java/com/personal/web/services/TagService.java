package com.personal.web.services;

import com.personal.web.dtos.CategoryAndTagResponseDto;
import com.personal.web.dtos.TagRequestDto;
import com.personal.web.entities.Tag;
import com.personal.web.exceptions.ConflictException;
import com.personal.web.exceptions.DuplicateException;
import com.personal.web.exceptions.ResourceNotFoundException;
import com.personal.web.mappers.CategoryAndTagMapper;
import com.personal.web.repositories.BlogRepository;
import com.personal.web.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@PreAuthorize("authentication.name == 'zulfan'")
public class TagService {
    private final TagRepository tagRepository;
    private final CategoryAndTagMapper categoryAndTagMapper;
    private final BlogRepository blogRepository;

    public CategoryAndTagResponseDto create(TagRequestDto requestDto){
        if(tagRepository.existsByName(requestDto.name())){
            throw new DuplicateException("Tag already exist");
        }

        Tag tag = new Tag();
        tag.setName(requestDto.name().toLowerCase());
        tagRepository.save(tag);
        return categoryAndTagMapper.toTagResponse(tag);
    }

    public List<CategoryAndTagResponseDto> getAll(){
        List<Tag> tags =  tagRepository.findAll();
        return tags.stream()
                .map(categoryAndTagMapper::toTagResponse)
                .toList();
    }

    public void delete(Long id){
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));
        if(blogRepository.existsByTags_id(id)){
            throw new ConflictException("Tag cannot be deleted because it is used by blogs");
        }
        tagRepository.delete(tag);
    }

    public CategoryAndTagResponseDto update(Long id, TagRequestDto request){
        Tag tag = tagRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Tag not found with id " + id));
        String newName = request.name().toLowerCase();

        if(tagRepository.existsByNameAndIdNot(newName, id)){
            throw new DuplicateException("Tag name already exist");
        }

        tag.setName(newName);
        tagRepository.save(tag);
        return categoryAndTagMapper.toTagResponse(tag);
    }
}
