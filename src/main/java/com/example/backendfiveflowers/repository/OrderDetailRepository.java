package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByProduct_ProductId(Integer productId);

    // Phương thức mới để tìm chi tiết đơn hàng trong khoảng thời gian
//    List<OrderDetail> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
