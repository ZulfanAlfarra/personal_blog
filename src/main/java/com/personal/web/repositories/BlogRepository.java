package com.personal.web.repositories;

import com.personal.web.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    boolean existsByTags_id(Long tagsId);

    boolean existsByCategory_Id(Long categoryId);
}
