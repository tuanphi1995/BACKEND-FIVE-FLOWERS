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

    public ProductImage updateImage(Integer id, MultipartFile file) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (!productImageOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy ảnh sản phẩm");
        }

        ProductImage productImage = productImageOptional.get();

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename));
        } catch (Exception e) {
            throw new RuntimeException("Không thể lưu trữ tệp. Lỗi: " + e.getMessage());
        }

        productImage.setImageUrl(this.root.resolve(filename).toString());
        return productImageRepository.save(productImage);
    }

    public void deleteProductImage(Integer id) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isPresent()) {
            ProductImage productImage = productImageOptional.get();
            // Xóa tệp hình ảnh khỏi hệ thống tệp
            Path filePath = Paths.get(productImage.getImageUrl());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                logger.error("Không thể xóa tệp: " + filePath, e);
            }
            // Xóa bản ghi khỏi cơ sở dữ liệu
            productImageRepository.deleteById(id);
        }
    }

    public Optional<ProductImage> getProductImageById(Integer id) {
        logger.info("Fetching product image with ID: " + id);
        Optional<ProductImage> productImage = productImageRepository.findById(id);
        if (productImage.isPresent()) {
            logger.info("Product image found: " + productImage.get());
        } else {
            logger.warn("Product image not found for ID: " + id);
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
            Path destinationFile = this.root.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            try {
                Files.copy(file.getInputStream(), destinationFile);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu trữ tệp. Lỗi: " + e.getMessage());
            }

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(destinationFile.toString());
            productImage.setProduct(product);

            savedProductImages.add(productImageRepository.save(productImage));
        }

        return savedProductImages;
    }

    public List<ProductImage> updateImages(int productId, MultipartFile[] files) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();
        // Lấy tất cả các ảnh hiện tại của sản phẩm này và xóa chúng
        List<ProductImage> existingImages = productImageRepository.findByProduct(product);
        for (ProductImage image : existingImages) {
            // Xóa tệp hình ảnh khỏi hệ thống tệp
            Path filePath = Paths.get(image.getImageUrl());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                logger.error("Không thể xóa tệp: " + filePath, e);
            }
            // Xóa bản ghi khỏi cơ sở dữ liệu
            productImageRepository.delete(image);
        }

        // Lưu trữ các ảnh mới
        List<ProductImage> updatedProductImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = this.root.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            try {
                Files.copy(file.getInputStream(), destinationFile);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu trữ tệp. Lỗi: " + e.getMessage());
            }

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(destinationFile.toString());
            productImage.setProduct(product);

            updatedProductImages.add(productImageRepository.save(productImage));
        }

        return updatedProductImages;
    }

    public List<ProductImage> getProductImagesByProductId(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        Product product = productOptional.get();
        return productImageRepository.findByProduct(product);
    }
}
