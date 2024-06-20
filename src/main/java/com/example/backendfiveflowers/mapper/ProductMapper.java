package com.example.backendfiveflowers.mapper;

import com.example.backendfiveflowers.dto.ProductDTO;
import com.example.backendfiveflowers.entity.Brand;
import com.example.backendfiveflowers.entity.Category;
import com.example.backendfiveflowers.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setColor(productDTO.getColor());

        Brand brand = new Brand();
        brand.setBrandId(Integer.parseInt(productDTO.getBrand()));
        product.setBrand(brand);

        Category category = new Category();
        category.setCategoryId(Integer.parseInt(productDTO.getCategory()));
        product.setCategory(category);

        return product;
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setColor(product.getColor());
        productDTO.setBrand(product.getBrand() != null ? String.valueOf(product.getBrand().getBrandId()) : null);
        productDTO.setCategory(product.getCategory() != null ? String.valueOf(product.getCategory().getCategoryId()) : null);
        return productDTO;
    }
}
