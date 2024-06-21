package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.repository.ProductImageRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
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

@Service
public class ProductImageService {

    private final Path root = Paths.get("uploads");

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

    public ProductImage addProductImage(ProductImage productImage) {
        Optional<Product> product = productRepository.findById(productImage.getProduct().getProductId());

        if (product.isPresent()) {
            productImage.setProduct(product.get());
            return productImageRepository.save(productImage);
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
    }

    public List<ProductImage> saveImages(MultipartFile[] files, int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();
        List<ProductImage> savedProductImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            try {
                Files.copy(file.getInputStream(), this.root.resolve(filename));
            } catch (Exception e) {
                throw new RuntimeException("Không thể lưu trữ tệp. Lỗi: " + e.getMessage());
            }

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(this.root.resolve(filename).toString());
            productImage.setProduct(product);

            savedProductImages.add(productImageRepository.save(productImage));
        }

        return savedProductImages;
    }

    public ProductImage updateProductImage(ProductImage productImage) {
        Optional<Product> product = productRepository.findById(productImage.getProduct().getProductId());

        if (product.isPresent()) {
            productImage.setProduct(product.get());
            return productImageRepository.save(productImage);
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
    }

    public ProductImage updateImage(Integer id, MultipartFile file) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (!productImageOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy ảnh sản phẩm");
        }

        ProductImage productImage = productImageOptional.get();

        String filename = file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename));
        } catch (Exception e) {
            throw new RuntimeException("Không thể lưu trữ tệp. Lỗi: " + e.getMessage());
        }

        productImage.setImageUrl(this.root.resolve(filename).toString());
        return productImageRepository.save(productImage);
    }

    public void deleteProductImage(Integer id) {
        productImageRepository.deleteById(id);
    }

    public Optional<ProductImage> getProductImageById(Integer id) {
        return productImageRepository.findById(id);
    }

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }
}
