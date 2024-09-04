package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.*;
import com.example.backendfiveflowers.event.OrderCreatedEvent;
import com.example.backendfiveflowers.event.OrderCancelledEvent;
import com.example.backendfiveflowers.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private AnalyticsVisitRepository analyticsVisitRepository;

    @Autowired
    private CartAdditionLogRepository cartAdditionLogRepository;

    @Transactional
    public Order addOrder(Order order) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(order.getUser().getId());
        if (!userInfoOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        order.setUser(userInfoOptional.get());

        Optional<Address> addressOptional = addressRepository.findById(order.getAddress().getAddressId());
        if (!addressOptional.isPresent()) {
            throw new RuntimeException("Address not found");
        }
        order.setAddress(addressOptional.get());

        Payment payment = order.getPayment();
        if (payment.getPaymentId() != 0) {
            Optional<Payment> paymentOptional = paymentRepository.findById(payment.getPaymentId());
            if (paymentOptional.isPresent()) {
                order.setPayment(paymentOptional.get());
            } else {
                throw new RuntimeException("Payment not found");
            }
        } else {
            payment = paymentRepository.save(payment);
            order.setPayment(payment);
        }

        double totalOrderPrice = 0.0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Optional<Product> productOptional = productRepository.findById(orderDetail.getProduct().getProductId());
            if (!productOptional.isPresent()) {
                throw new RuntimeException("Product not found");
            }
            Product product = productOptional.get();
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);
            orderDetail.setStatus("Pending");

            double detailPrice = product.getPrice() * orderDetail.getQuantity();
            orderDetail.setPrice(detailPrice);
            totalOrderPrice += detailPrice;
        }

        double shippingCost = 5.0;
        order.setShippingCost(shippingCost);
        order.setPrice(totalOrderPrice + shippingCost);

        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("Pending");
        }

        Order savedOrder = orderRepository.save(order);

        eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));

        return savedOrder;
    }

    @Transactional
    public Order updateOrder(Integer id, Order order) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (!existingOrderOptional.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        Order existingOrder = existingOrderOptional.get();
        existingOrder.setUser(order.getUser());
        existingOrder.setAddress(order.getAddress());
        existingOrder.setStatus(order.getStatus());

        double totalOrderPrice = 0.0;
        existingOrder.getOrderDetails().clear();

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Optional<Product> productOptional = productRepository.findById(orderDetail.getProduct().getProductId());
            if (!productOptional.isPresent()) {
                throw new RuntimeException("Product not found");
            }
            Product product = productOptional.get();
            orderDetail.setProduct(product);
            orderDetail.setOrder(existingOrder);

            double detailPrice = product.getPrice() * orderDetail.getQuantity();
            orderDetail.setPrice(detailPrice);
            totalOrderPrice += detailPrice;

            existingOrder.getOrderDetails().add(orderDetail);
        }

        existingOrder.setPrice(totalOrderPrice);

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order updateOrderStatus(Integer id, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            for (OrderDetail detail : order.getOrderDetails()) {
                detail.setStatus(status);
                orderDetailRepository.save(detail);
            }

            if ("Cancelled".equals(status)) {
                eventPublisher.publishEvent(new OrderCancelledEvent(this, order));
            }

            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    public List<Map<String, Object>> getTopSellingProductsToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<Order> orders = orderRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
        Map<Integer, Map<String, Object>> productCountMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderDetail detail : order.getOrderDetails()) {
                int productId = detail.getProduct().getProductId();
                productCountMap.putIfAbsent(productId, new HashMap<>());
                Map<String, Object> productInfo = productCountMap.get(productId);

                productInfo.put("name", detail.getProduct().getName());
                productInfo.put("imageUrl", detail.getProduct().getProductImages().get(0));
                productInfo.put("price", detail.getProduct().getPrice());

                int currentCount = (int) productInfo.getOrDefault("count", 0);
                productInfo.put("count", currentCount + detail.getQuantity());
            }
        }

        List<Map<String, Object>> topSellingProducts = new ArrayList<>(productCountMap.values());
        topSellingProducts.sort((a, b) -> (int) b.get("count") - (int) a.get("count"));

        return topSellingProducts.stream().limit(3).collect(Collectors.toList());
    }


    public Map<String, Double> getDailySalesTotals(LocalDate startDate, LocalDate endDate) {
        Map<String, Double> dailySalesTotals = new LinkedHashMap<>();

        while (!startDate.isAfter(endDate)) {
            LocalDateTime startOfDay = startDate.atStartOfDay();
            LocalDateTime endOfDay = startDate.atTime(LocalTime.MAX);
            List<Order> orders = orderRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
            double totalSales = orders.stream().mapToDouble(Order::getPrice).sum();
            dailySalesTotals.put(startDate.toString(), totalSales);
            startDate = startDate.plusDays(1);
        }

        return dailySalesTotals;
    }

    public List<Map<String, Object>> getTopSellingProducts(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);

        List<Order> orders = orderRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
        Map<Integer, Map<String, Object>> productCountMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderDetail detail : order.getOrderDetails()) {
                int productId = detail.getProduct().getProductId();
                productCountMap.putIfAbsent(productId, new HashMap<>());
                Map<String, Object> productInfo = productCountMap.get(productId);

                productInfo.put("name", detail.getProduct().getName());
                productInfo.put("imageUrl", detail.getProduct().getProductImages().get(0).getImageUrl());
                productInfo.put("price", detail.getProduct().getPrice());
                productInfo.put("brand", detail.getProduct().getBrand().getName());
                productInfo.put("category", detail.getProduct().getCategory().getName());

                int currentCount = (int) productInfo.getOrDefault("quantitySold", 0);
                productInfo.put("quantitySold", currentCount + detail.getQuantity());
            }
        }

        List<Map<String, Object>> topSellingProducts = new ArrayList<>(productCountMap.values());
        topSellingProducts.sort((a, b) -> (int) b.get("quantitySold") - (int) a.get("quantitySold"));

        return topSellingProducts.stream().limit(3).collect(Collectors.toList());
    }


    public int getPendingOrdersCount(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Order> orders = orderRepository.findAllByCreatedAtBetweenAndStatus(startOfDay, endOfDay, "Pending");
        return orders.size();
    }

    public int getNewOrdersCount(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Order> orders = orderRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
        return orders.size();
    }

    public Map<String, Object> getSummary(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> summary = new HashMap<>();

        int totalNewOrders = orderRepository.findAllByCreatedAtBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)).size();
        int totalPendingOrders = orderRepository.findAllByCreatedAtBetweenAndStatus(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX), "Pending").size();
        double totalSales = orderRepository.findAllByCreatedAtBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)).stream().mapToDouble(Order::getPrice).sum();
        int totalVisitors = analyticsVisitRepository.countByVisitTimeBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        int addToCartCount = cartAdditionLogRepository.findByDateBetween(startDate, endDate).size();

        summary.put("totalSales", totalSales);
        summary.put("newOrders", totalNewOrders);
        summary.put("pendingOrders", totalPendingOrders);
        summary.put("visitors", totalVisitors);
        summary.put("addToCart", addToCartCount);

        return summary;
    }
    // Hàm để đếm số lượng đơn hàng theo trạng thái
    public Map<String, Integer> getOrderCountByStatus(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        Map<String, Integer> statusCountMap = new HashMap<>();

        for (Order order : orders) {
            String status = order.getStatus();
            statusCountMap.put(status, statusCountMap.getOrDefault(status, 0) + 1);
        }

        return statusCountMap;
    }
}