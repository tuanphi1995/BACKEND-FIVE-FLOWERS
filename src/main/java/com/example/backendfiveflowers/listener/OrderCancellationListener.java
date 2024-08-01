package com.example.backendfiveflowers.listener;

import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.event.OrderCancelledEvent;
import com.example.backendfiveflowers.model.EmailRequest;
import com.example.backendfiveflowers.service.EmailService;
import com.example.backendfiveflowers.service.EmailContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCancellationListener implements ApplicationListener<OrderCancelledEvent> {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailContentBuilder emailContentBuilder;

    private final String adminEmail = "tuanphi1995@gmail.com"; // Địa chỉ email của admin

    @Override
    public void onApplicationEvent(OrderCancelledEvent event) {
        Order order = event.getOrder();
        String customerEmail = order.getUser().getEmail();
        String subject = "Order Cancellation #" + order.getOrderId();
        String body = emailContentBuilder.buildOrderCancellationEmail(order); // Sử dụng phương thức mới

        // Gửi email cho khách hàng
        EmailRequest customerEmailRequest = new EmailRequest(customerEmail, subject, body);
        emailService.sendHtmlEmail(customerEmailRequest);

        // Gửi email cho admin
        String adminSubject = "Order Cancelled #" + order.getOrderId();
        String adminBody = emailContentBuilder.buildOrderCancellationEmail(order); // Sử dụng phương thức mới
        EmailRequest adminEmailRequest = new EmailRequest(adminEmail, adminSubject, adminBody);
        emailService.sendHtmlEmail(adminEmailRequest);
    }
}
