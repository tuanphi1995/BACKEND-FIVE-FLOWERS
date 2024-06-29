package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Media;
import com.example.backendfiveflowers.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    private static final Logger LOGGER = Logger.getLogger(MediaController.class.getName());

    @PostMapping("/upload")
    public ResponseEntity<Media> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            LOGGER.info("Received file upload request: " + file.getOriginalFilename());
            Media media = mediaService.store(file);
            return ResponseEntity.ok(media);
        } catch (IOException e) {
            LOGGER.severe("Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Media>> getAllMedia() {
        return ResponseEntity.ok(mediaService.getAllMedia());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Media>> getAllMediaPaged(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(mediaService.getAllMediaPaged(PageRequest.of(page, size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Media> updateMedia(@PathVariable Long id, @RequestBody Media mediaDetails) {
        Media updatedMedia = mediaService.updateMedia(id, mediaDetails);
        return ResponseEntity.ok(updatedMedia);
    }
}
