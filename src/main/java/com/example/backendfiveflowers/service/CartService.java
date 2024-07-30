package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.CartAdditionLog;
import com.example.backendfiveflowers.model.CartItemRequest;
import com.example.backendfiveflowers.repository.CartAdditionLogRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {

    private Map<String, List<CartItemRequest>> userCarts = new ConcurrentHashMap<>();
    private final String CART_FILE_PATH = "src/data/cartData.json"; // Đường dẫn tùy chỉnh
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CartAdditionLogRepository cartAdditionLogRepository;

    @PostConstruct
    public void init() {
        loadCartsFromFile();
    }

    public void syncCart(List<CartItemRequest> cartItems, String username) {
        userCarts.put(username, cartItems);
        saveCartsToFile();
        updateCartAdditionLog();
    }

    public List<CartItemRequest> getCart(String username) {
        return userCarts.getOrDefault(username, new ArrayList<>());
    }

    public void clearCart(String username) {
        userCarts.remove(username);
        saveCartsToFile();
    }

    public void removeCartItem(String username, Long productId) {
        List<CartItemRequest> cartItems = userCarts.get(username);
        if (cartItems != null) {
            cartItems.removeIf(item -> item.getProductId().equals(productId));
            userCarts.put(username, cartItems);
            saveCartsToFile();
        }
    }

    private void loadCartsFromFile() {
        try {
            File file = new File(CART_FILE_PATH);
            if (file.exists()) {
                userCarts = objectMapper.readValue(file, new TypeReference<Map<String, List<CartItemRequest>>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCartsToFile() {
        try {
            File file = new File(CART_FILE_PATH);
            // Tạo thư mục nếu chưa tồn tại
            file.getParentFile().mkdirs();
            objectMapper.writeValue(file, userCarts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCartAdditionLog() {
        LocalDate today = LocalDate.now();
        CartAdditionLog log = cartAdditionLogRepository.findByDate(today);
        if (log == null) {
            log = new CartAdditionLog(null, today, 1, LocalDateTime.now());
        } else {
            log.setCount(log.getCount() + 1);
            log.setUpdateTime(LocalDateTime.now());
        }
        cartAdditionLogRepository.save(log);
    }
    public int getTotalAddToCartByDate(LocalDate date) {
        CartAdditionLog log = cartAdditionLogRepository.findByDate(date);
        return log != null ? log.getCount() : 0;
    }

}
