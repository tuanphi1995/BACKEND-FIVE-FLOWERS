package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Media;
import com.example.backendfiveflowers.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MediaService {

    private static final Logger LOGGER = Logger.getLogger(MediaService.class.getName());

    // Đường dẫn tuyệt đối đến thư mục lưu trữ ảnh
    private final String uploadDir = "/Users/macbookprocuaphi/Documents/fEproject02/reactDoAnKy2/FONTEND-FIVE-FLOWERS/fontend-five-flowers/src/admin/media/img";
    private Path root;

    @Autowired
    private MediaRepository mediaRepository;

    @PostConstruct
    public void init() throws IOException {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
        LOGGER.info("Upload directory: " + this.root.toString());
        if (!Files.exists(root)) {
            Files.createDirectories(root); // Đảm bảo thư mục tồn tại
            LOGGER.info("Directory created: " + this.root.toString());
        } else {
            LOGGER.info("Directory already exists: " + this.root.toString());
        }
    }

    public Media store(MultipartFile file) throws IOException {
        LOGGER.info("Received file: " + file.getOriginalFilename());
        LOGGER.info("File size: " + file.getSize());
        Path targetLocation = this.root.resolve(file.getOriginalFilename());
        LOGGER.info("Saving file to: " + targetLocation.toString());

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("File successfully copied to target location.");
            Media media = new Media(file.getOriginalFilename(), file.getContentType(), "/admin/media/img/" + file.getOriginalFilename());
            LOGGER.info("File saved successfully: " + media.getFilePath());
            return mediaRepository.save(media);
        } catch (IOException e) {
            LOGGER.severe("Failed to save file: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Unexpected error: " + e.getMessage());
            throw new IOException("Unexpected error during file saving", e);
        }
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
        return mediaRepository.save(media);
    }
}
