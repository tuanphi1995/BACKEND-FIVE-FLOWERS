package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Media;
import com.example.backendfiveflowers.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Media>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            LOGGER.info("Received file upload request");
            List<Media> mediaList = mediaService.storeFiles(files);
            return ResponseEntity.ok(mediaList);
        } catch (IOException e) {
            LOGGER.severe("Failed to upload files: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Media>> getAllMedia() {
        return ResponseEntity.ok(mediaService.getAllMedia());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Media> updateMedia(@PathVariable Long id, @RequestBody Media mediaDetails) {
        Media updatedMedia = mediaService.updateMedia(id, mediaDetails);
        return ResponseEntity.ok(updatedMedia);
    }
}
