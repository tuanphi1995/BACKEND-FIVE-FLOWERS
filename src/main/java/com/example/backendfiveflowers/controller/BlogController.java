package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Blog;
import com.example.backendfiveflowers.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog addBlog(@RequestBody Blog blog) {
        return blogService.addBlog(blog);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog updateBlog(@PathVariable Integer id, @RequestBody Blog blog) {
        blog.setBlogId(id); // Đảm bảo blog ID được đặt từ URL
        return blogService.updateBlog(blog);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog getBlogById(@PathVariable Integer id) {
        return blogService.getBlogById(id).orElse(null);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogService.getAllBlogs(pageable);
    }
}
