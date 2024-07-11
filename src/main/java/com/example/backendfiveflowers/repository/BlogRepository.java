package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Query("SELECT b FROM Blog b ORDER BY b.createdAt DESC")
    Page<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
