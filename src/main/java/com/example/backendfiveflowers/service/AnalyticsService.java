package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.repository.AnalyticsVisitRepository;
import com.example.backendfiveflowers.repository.OrderRepository;
import com.example.backendfiveflowers.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsVisitRepository visitRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    public void saveVisit(AnalyticsVisit visit) {
        visitRepository.save(visit);
        System.out.println("Visit saved: " + visit);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, Object> getStatsByDate(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59, 999999999);

        // Lấy danh sách visits trong khoảng thời gian
        List<AnalyticsVisit> visits = visitRepository.findAll().stream()
                .filter(v -> !v.getVisitTime().isBefore(startDate) && !v.getVisitTime().isAfter(endDate))
                .collect(Collectors.toList());

        // Tính toán số liệu thống kê
        double totalSale = calculateTotalSale(startDate, endDate);
        double conversionRate = calculateConversionRate(startDate, endDate);
        long addToCart = calculateAddToCart(startDate, endDate);
        long visitor = visits.size();

        // Tạo map để trả về
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSale", totalSale);
        stats.put("conversionRate", conversionRate);
        stats.put("addToCart", addToCart);
        stats.put("visitor", visitor);

        return stats;
    }

    private double calculateTotalSale(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findAll().stream()
                .filter(o -> !o.getCreatedAt().isBefore(startDate) && !o.getCreatedAt().isAfter(endDate))
                .mapToDouble(Order::getPrice)
                .sum();
    }

    private double calculateConversionRate(LocalDateTime startDate, LocalDateTime endDate) {
        long totalVisitors = visitRepository.count();
        long totalOrders = orderRepository.findAll().stream()
                .filter(o -> !o.getCreatedAt().isBefore(startDate) && !o.getCreatedAt().isAfter(endDate))
                .count();
        return totalVisitors == 0 ? 0 : ((double) totalOrders / totalVisitors) * 100;
    }

    private long calculateAddToCart(LocalDateTime startDate, LocalDateTime endDate) {
        return cartRepository.countByAddToCartTimeBetween(startDate, endDate);
    }
}
