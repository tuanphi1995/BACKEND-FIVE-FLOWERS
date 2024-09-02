package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${open.api.url}")
    private String apiURL;

    @Value("${flask.base.url}")
    private String flaskBaseUrl;

    @Autowired
    private RestTemplate template;

    @Autowired
    private ProductService productService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/learn")
    public String learn(@RequestBody Map<String, Object> text) {
        String url = flaskBaseUrl + "/learn";
        ResponseEntity<String> response = restTemplate.postForEntity(url, text, String.class);
        return response.getBody();
    }

    @PostMapping("/ask")
    public String ask(@RequestBody Map<String, Object> question) {
        String url = flaskBaseUrl + "/ask";
        ResponseEntity<String> response = restTemplate.postForEntity(url, question, String.class);
        return response.getBody();
    }

    @PostMapping("/learn_image")
    public String learnImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "No image provided";
        }

        try {
            String url = flaskBaseUrl + "/learn_from_image";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Gửi request đến Flask và nhận phản hồi
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            return response.getBody();
        } catch (IOException e) {
            return "Failed to process image";
        }
    }

    @PostMapping("/upload_file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }

        try {
            String url = flaskBaseUrl + "/upload_file";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            // Bắt và xử lý lỗi từ Flask server
            return ResponseEntity.status(e.getStatusCode()).body("Error processing file: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process file");
        }
    }

}
