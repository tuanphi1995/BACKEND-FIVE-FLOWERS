package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Media;
import com.example.backendfiveflowers.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MediaService {

    private static final Logger LOGGER = Logger.getLogger(MediaService.class.getName());

    private final String uploadDir = "/Users/macbookprocuaphi/Documents/ractDoAnKy02/REAL/FONTEND-FIVE-FLOWERS/fontend-five-flowers/public/media";
    private final String mediaListFile = "/Users/macbookprocuaphi/Documents/ractDoAnKy02/REAL/FONTEND-FIVE-FLOWERS/fontend-five-flowers/public/media/mediaList.json";
    private Path root;

    @Autowired
    private MediaRepository mediaRepository;

    @PostConstruct
    public void init() throws IOException {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
        LOGGER.info("Upload directory: " + this.root.toString());
        if (!Files.exists(root)) {
            Files.createDirectories(root);
            LOGGER.info("Directory created: " + this.root.toString());
        } else {
            LOGGER.info("Directory already exists: " + this.root.toString());
        }
    }

    public List<Media> storeFiles(MultipartFile[] files) throws IOException {
        List<Media> savedMediaList = new ArrayList<>();
        for (MultipartFile file : files) {
            LOGGER.info("Received file: " + file.getOriginalFilename());
            Path targetLocation = this.root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Media media = new Media(file.getOriginalFilename(), file.getContentType(), "/media/" + file.getOriginalFilename());
            Media savedMedia = mediaRepository.save(media);
            savedMediaList.add(savedMedia);
        }
        updateMediaListJson();
        return savedMediaList;
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Page<Media> getAllMediaPaged(Pageable pageable) {
        return mediaRepository.findAll(pageable);
    }

    public Media updateMedia(Long id, Media mediaDetails) {
        Media media = mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found"));
        media.setFileName(mediaDetails.getFileName());
        Media updatedMedia = mediaRepository.save(media);
        updateMediaListJson();
        return updatedMedia;
    }

    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
        updateMediaListJson();
    }

    private void updateMediaListJson() {
        List<Media> mediaList = mediaRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for (Media media : mediaList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", media.getId());
            jsonObject.put("fileName", media.getFileName());
            jsonObject.put("filePath", media.getFilePath());
            jsonObject.put("createdAt", media.getCreatedAt().toString());
            jsonArray.put(jsonObject);
        }
        try (FileWriter file = new FileWriter(mediaListFile)) {
            file.write(jsonArray.toString(2));
        } catch (IOException e) {
            LOGGER.severe("Error writing JSON file: " + e.getMessage());
        }
    }
}
