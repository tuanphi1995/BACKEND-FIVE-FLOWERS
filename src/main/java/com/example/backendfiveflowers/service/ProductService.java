package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Brand;
import com.example.backendfiveflowers.entity.Category;
import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.ProductImage;
import com.example.backendfiveflowers.repository.BrandRepository;
import com.example.backendfiveflowers.repository.CategoryRepository;
import com.example.backendfiveflowers.repository.ProductImageRepository;
import com.example.backendfiveflowers.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageService productImageService;

    public Product addProduct(Product product) {
        Optional<Brand> brand = brandRepository.findById(product.getBrand().getBrandId());
        Optional<Category> category = categoryRepository.findById(product.getCategory().getCategoryId());

        if (brand.isPresent() && category.isPresent()) {
            product.setBrand(brand.get());
            product.setCategory(category.get());
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Not found brand or category");
        }
    }

    public Product updateProduct(Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(productDetails.getProductId());
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setQuantity(productDetails.getQuantity());
            product.setColor(productDetails.getColor());

            Optional<Brand> brand = brandRepository.findById(productDetails.getBrand().getBrandId());
            Optional<Category> category = categoryRepository.findById(productDetails.getCategory().getCategoryId());

            if (brand.isPresent() && category.isPresent()) {
                product.setBrand(brand.get());
                product.setCategory(category.get());
            } else {
                throw new RuntimeException("Not found brand or category");
            }

            Product updatedProduct = productRepository.save(product);

            // Cập nhật các URL ảnh sản phẩm nếu có
            if (productDetails.getProductImages() != null && !productDetails.getProductImages().isEmpty()) {
                List<String> imageUrls = productDetails.getProductImages().stream()
                        .map(ProductImage::getImageUrl)
                        .collect(Collectors.toList());
                productImageService.updateExistingImages(productDetails.getProductId(), imageUrls);
            }

            return updatedProduct;
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm với id: " + productDetails.getProductId());
        }
    }



    public void deleteProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            existingProduct.setDeleted(true); // Sử dụng setter setDeleted
            productRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm với id: " + id);
        }
    }


    public Optional<Product> getProductById(Integer id) {
        return productRepository.findByProductIdAndIsDeletedFalse(id);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllByIsDeletedFalse(pageable);
    }

    public void reduceQuantity(int productId, int quantity) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            int newQuantity = product.getQuantity() - quantity;
            if (newQuantity >= 0) {
                product.setQuantity(newQuantity);
                productRepository.save(product);
            } else {
                throw new RuntimeException("Không đủ hàng trong kho");
            }
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm với id: " + productId);
        }
    }

    public void addExistingImages(int productId, List<String> imageUrls) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOptional.get();
        for (String imageUrl : imageUrls) {
            // Kiểm tra xem ảnh đã tồn tại chưa
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

        // Thêm các ảnh mới
        for (String imageUrl : imageUrls) {
            if (!existingImages.stream().anyMatch(img -> img.getImageUrl().equals(imageUrl))) {
                ProductImage newImage = new ProductImage();
                newImage.setImageUrl(imageUrl);
                newImage.setProduct(product);
                productImageRepository.save(newImage);
            }
        }
    }

    public Page<Product> searchProducts(String name, Pageable pageable) {
        return productRepository.findByNameContainingAndIsDeletedFalse(name, pageable);
    }

}