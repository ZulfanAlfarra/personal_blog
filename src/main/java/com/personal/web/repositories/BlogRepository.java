package com.personal.web.repositories;

import com.personal.web.entities.Blog;
import com.personal.web.entities.BlogStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    boolean existsByTags_id(Long tagsId);

    boolean existsByCategory_Id(Long categoryId);

    List<Blog> findByStatus(BlogStatus status, Pageable pageable);
}
