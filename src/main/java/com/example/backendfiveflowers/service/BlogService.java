package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Blog;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.model.Article;
import com.example.backendfiveflowers.model.NewsResponse;
import com.example.backendfiveflowers.repository.BlogRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public BlogService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

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

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Blog updateBlog(Integer id, Blog blog) {
        Optional<Blog> existingBlog = blogRepository.findById(id);
        if (existingBlog.isPresent()) {
            Blog updatedBlog = existingBlog.get();
            updatedBlog.setTitle(blog.getTitle());
            updatedBlog.setContent(blog.getContent());
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

    public List<Blog> fetchBicycleNews() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/everything?q=bicycle&apiKey=d8a0a2831ea443a1b746f7cdcd0c8e1b"; // Thay YOUR_NEWSAPI_KEY bằng API key của bạn
        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);

        if (response != null && response.getArticles() != null) {
            response.getArticles().forEach(article -> {
                String summarizedContent = summarizeContent(article.getDescription());

                Blog blog = new Blog();
                blog.setTitle(article.getTitle());
                blog.setContent(summarizedContent);
                blog.setImageUrl(article.getUrlToImage());
                blog.setCreatedAt(LocalDateTime.now());
                blog.setUpdatedAt(LocalDateTime.now());

                String username = getCurrentUsername();
                Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserName(username);
                userInfoOptional.ifPresent(blog::setAuthor);

                blogRepository.save(blog);
            });
        }
        return blogRepository.findAll();
    }

    // Phương thức tìm kiếm bài viết theo từ khóa
    public NewsResponse searchArticles(String keyword) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/everything?q=" + keyword + "&sortBy=publishedAt&apiKey=d8a0a2831ea443a1b746f7cdcd0c8e1b";
        return restTemplate.getForObject(url, NewsResponse.class);
    }

    // Phương thức xử lý và lưu bài viết từ kết quả tìm kiếm
    public void processAndSaveArticle(Article article) {
        String summarizedContent = summarizeContent(article.getDescription());

        Blog blog = new Blog();
        blog.setTitle(article.getTitle());
        blog.setContent(summarizedContent);
        blog.setImageUrl(article.getUrlToImage());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());

        String username = getCurrentUsername();
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserName(username);
        userInfoOptional.ifPresent(blog::setAuthor);

        blogRepository.save(blog);
    }

    private String summarizeContent(String content) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/completions";
        String apiKey = "sk-proj-4jth32x6Whl4T6P3OEtdT3BlbkFJWq8zkqeBPqMoyY5qfsHV"; // Thay YOUR_OPENAI_API_KEY bằng API key của bạn

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-3.5-turbo"); // Sử dụng model mới
        requestPayload.put("prompt", "Summarize this content: " + content);
        requestPayload.put("max_tokens", 100);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    if (!choices.isEmpty()) {
                        return (String) choices.get(0).get("text");
                    }
                }
            }
        } catch (HttpClientErrorException.TooManyRequests e) {
            System.out.println("Quota exceeded. Please check your OpenAI API quota and billing details.");
            return "Quota exceeded. Unable to summarize content.";
        }

        return content;
    }
}
