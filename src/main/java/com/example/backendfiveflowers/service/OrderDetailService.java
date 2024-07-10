package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.OrderDetail;
import com.example.backendfiveflowers.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public void deleteOrderDetail(Integer id) {
        Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(id);
        if (orderDetailOptional.isPresent()) {
            orderDetailRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order detail not found");
        }
    }

    public Optional<OrderDetail> getOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    public Page<OrderDetail> getAllOrderDetails(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }
    public List<OrderDetail> getOrderDetailsByProductId(Integer productId) {
        return orderDetailRepository.findByProduct_ProductId(productId);
    }

}
