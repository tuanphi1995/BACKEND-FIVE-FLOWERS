package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Blog;
import com.example.backendfiveflowers.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/blogs")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/add")
    public Blog addBlog(@RequestBody Blog blog) {
        return blogService.addBlog(blog);
    }

    @PutMapping("/update")
    public Blog updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
    }

    @GetMapping("/get/{id}")
    public Blog getBlogById(@PathVariable Integer id) {
        return blogService.getBlogById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }
}
