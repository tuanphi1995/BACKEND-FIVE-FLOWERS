package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.repository.ProductImageRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.UUID;

@Service
public class ProductImageService {

    private final Path root = Paths.get("uploads");
    private static final Logger logger = LoggerFactory.getLogger(ProductImageService.class);

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductImageService() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Không thể khởi tạo thư mục để tải lên!");
        }
    }

    public List<ProductImage> updateImages(int productId, MultipartFile[] files) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();
        List<ProductImage> existingImages = productImageRepository.findByProduct(product);

        // Chỉ thêm các ảnh mới
        for (MultipartFile file : files) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = this.root.resolve(filename).normalize().toAbsolutePath();
            try {
                Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu trữ tệp. Lỗi: " + e.getMessage());
            }

            ProductImage newImage = new ProductImage();
            newImage.setImageUrl(filename);
            newImage.setProduct(product);
            productImageRepository.save(newImage);
        }

        return productImageRepository.findByProduct(product);
    }


    public void deleteProductImage(Integer id) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isPresent()) {
            ProductImage productImage = productImageOptional.get();
            Path filePath = Paths.get(productImage.getImageUrl());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                logger.error("Không thể xóa tệp: " + filePath, e);
            }
            productImageRepository.deleteById(id);
        }
    }

    public Optional<ProductImage> getProductImageById(Integer id) {
        logger.info("Đang tìm nạp ảnh sản phẩm với ID: " + id);
        Optional<ProductImage> productImage = productImageRepository.findById(id);
        if (productImage.isPresent()) {
            logger.info("Đã tìm thấy ảnh sản phẩm: " + productImage.get());
        } else {
            logger.warn("Không tìm thấy ảnh sản phẩm với ID: " + id);
        }
        return productImage;
    }

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    public List<ProductImage> saveImages(MultipartFile[] files, int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();
        List<ProductImage> savedProductImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = this.root.resolve(filename).normalize().toAbsolutePath();
            try {
                Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu trữ tệp. Lỗi: " + e.getMessage());
            }

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(filename);
            productImage.setProduct(product);

            savedProductImages.add(productImageRepository.save(productImage));
        }

        return savedProductImages;
    }

    public List<ProductImage> getProductImagesByProductId(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        Product product = productOptional.get();
        return productImageRepository.findByProduct(product);
    }

    public void addExistingImages(int productId, List<String> imageUrls) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();
        for (String imageUrl : imageUrls) {
            if (!productImageRepository.existsByImageUrlAndProduct(imageUrl, product)) {
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(imageUrl);
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
        }
    }

    public void updateExistingImages(int productId, List<String> imageUrls) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();
        List<ProductImage> existingImages = productImageRepository.findByProduct(product);

        // Xóa các ảnh cũ không còn trong danh sách mới
        for (ProductImage existingImage : existingImages) {
            if (!imageUrls.contains(existingImage.getImageUrl())) {
                productImageRepository.delete(existingImage);
            }
        }

        // Thêm các ảnh mới chưa có trong danh sách
        for (String imageUrl : imageUrls) {
            if (!existingImages.stream().anyMatch(img -> img.getImageUrl().equals(imageUrl))) {
                ProductImage newImage = new ProductImage();
                newImage.setImageUrl(imageUrl);
                newImage.setProduct(product);
                productImageRepository.save(newImage);
            }
        }
    }

}