package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blogs, Long> {
}
