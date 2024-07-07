package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Blog;
import com.example.backendfiveflowers.service.BlogCrawlingService;
import com.example.backendfiveflowers.service.BlogService;
import com.example.backendfiveflowers.service.NewsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogCrawlingService blogCrawlingService;

    @Autowired
    private NewsSearchService newsSearchService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog addBlog(@RequestBody Blog blog) {
        return blogService.addBlog(blog);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = blogService.storeFile(file);
        return ResponseEntity.ok(fileName);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog updateBlog(@PathVariable Integer id, @RequestBody Blog blog) {
        return blogService.updateBlog(id, blog);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
    }

    @GetMapping("/get/{id}")
    public Blog getBlogById(@PathVariable Integer id) {
        return blogService.getBlogById(id).orElse(null);
    }

    @GetMapping("/all")
    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogService.getAllBlogs(pageable);
    }

    // Thêm endpoint để tìm kiếm và lưu trữ tin tức
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> searchNews(@RequestParam String query) {
        try {
            newsSearchService.searchAndSaveNews(query);
            return ResponseEntity.ok("Search and save completed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error during search: " + e.getMessage());
        }
    }
}
