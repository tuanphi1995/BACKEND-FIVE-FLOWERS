package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Blog;
import com.example.backendfiveflowers.model.Article;
import com.example.backendfiveflowers.model.NewsResponse;
import com.example.backendfiveflowers.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/fetch-news")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Blog> fetchNews() {
        return blogService.fetchBicycleNews();
    }

    @GetMapping("/search")
    public NewsResponse searchArticles(@RequestParam String keyword) {
        return blogService.searchArticles(keyword);
    }

    @PostMapping("/process-article")
    public Blog processAndSaveArticle(@RequestBody Article article) {
        return blogService.processAndSaveArticle(article);
    }
}
