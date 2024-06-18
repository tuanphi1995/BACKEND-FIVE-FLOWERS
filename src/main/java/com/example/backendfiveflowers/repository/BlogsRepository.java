package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<Blogs, Long> {
}
