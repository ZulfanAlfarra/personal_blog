package com.personal.web.repositories;

import com.personal.web.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Boolean existsByName(String name);

    Boolean existsByNameAndIdNot(String name, Long id);
}
