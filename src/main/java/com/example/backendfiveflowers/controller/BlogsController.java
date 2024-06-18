package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Blogs;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogsController {

    @Autowired
    private BlogsService blogsService;

    @GetMapping
    public Page<Blogs> getAllBlogs(Pageable pageable) {
        return blogsService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Blogs> getBlogById(@PathVariable Long id) {
        return blogsService.findById(id);
    }

    @PostMapping
    public Blogs createBlog(@RequestBody Blogs blogs) {
        return blogsService.save(blogs);
    }

    @PutMapping("/{id}")
    public Blogs updateBlog(@PathVariable Long id, @RequestBody Blogs blogDetails) {
        Blogs blogs = blogsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        blogs.setTitle(blogDetails.getTitle());
        blogs.setContent(blogDetails.getContent());
        blogs.setAuthor(blogDetails.getAuthor());
        return blogsService.save(blogs);
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id) {
        blogsService.deleteById(id);
    }
}
