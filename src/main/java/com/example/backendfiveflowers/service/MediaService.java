package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Media;
import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.repository.MediaRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MediaService {

    private static final Logger LOGGER = Logger.getLogger(MediaService.class.getName());
    private final String uploadDir = "/Users/macbookprocuaphi/Documents/ractDoAnKy02/REAL/FONTEND-FIVE-FLOWERS/fontend-five-flowers/public/media";
    private Path root;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ProductRepository productRepository;

    public MediaService() throws IOException {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
    }

    public List<Media> storeFiles(MultipartFile[] files, int productId) throws IOException {
        List<Media> savedMediaList = new ArrayList<>();
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            for (MultipartFile file : files) {
                Path targetLocation = this.root.resolve(file.getOriginalFilename());
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                Media media = new Media(file.getOriginalFilename(), file.getContentType(), "/media/" + file.getOriginalFilename());
                media.setProduct(product); // Gán product cho media
                Media savedMedia = mediaRepository.save(media);
                savedMediaList.add(savedMedia);
            }
        }

        return savedMediaList;
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }
}
