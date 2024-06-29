package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Page<Media> findAll(Pageable pageable);
}
