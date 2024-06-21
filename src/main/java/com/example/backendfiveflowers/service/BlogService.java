package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Blog;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.BlogRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Blog addBlog(Blog blog) {
        String username = getCurrentUsername();
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserName(username);
        if (userInfoOptional.isPresent()) {
            blog.setAuthor(userInfoOptional.get());
        } else {
            throw new RuntimeException("User not found");
        }

        return blogRepository.save(blog);
    }

    public Blog updateBlog(Blog blog) {
        Optional<Blog> existingBlog = blogRepository.findById(blog.getBlogId());
        if (existingBlog.isPresent()) {
            Blog updatedBlog = existingBlog.get();
            updatedBlog.setTitle(blog.getTitle());
            updatedBlog.setContent(blog.getContent());

            Optional<UserInfo> author = userInfoRepository.findById(blog.getAuthor().getId());
            if (author.isPresent()) {
                updatedBlog.setAuthor(author.get());
            } else {
                throw new RuntimeException("Author not found");
            }

            return blogRepository.save(updatedBlog);
        } else {
            throw new RuntimeException("Blog not found");
        }
    }

    public void deleteBlog(Integer id) {
        blogRepository.deleteById(id);
    }

    public Optional<Blog> getBlogById(Integer id) {
        return blogRepository.findById(id);
    }

    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
