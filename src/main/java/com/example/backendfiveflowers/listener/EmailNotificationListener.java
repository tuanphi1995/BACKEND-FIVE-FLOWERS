package com.example.backendfiveflowers.listener;

import com.example.backendfiveflowers.entity.Order;
import com.example.backendfiveflowers.event.OrderCreatedEvent;
import com.example.backendfiveflowers.model.EmailRequest;
import com.example.backendfiveflowers.service.EmailService;
import com.example.backendfiveflowers.service.EmailContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener implements ApplicationListener<OrderCreatedEvent> {

    @Autowired
    private EmailService emailService;

    private final String adminEmail = "tuanphi1995@gmail.com";

    @Autowired
    private EmailContentBuilder emailContentBuilder;

    @Override
    public void onApplicationEvent(OrderCreatedEvent event) {
        Order order = event.getOrder();
        String customerEmail = order.getUser().getEmail();
        String subject = "Order confirmation #" + order.getOrderId();
        String body = emailContentBuilder.buildOrderConfirmationEmail(order);

        EmailRequest emailRequest = new EmailRequest(customerEmail, subject, body);
        emailService.sendHtmlEmail(emailRequest);

        // Gửi email cho admin
        String adminSubject = "New Order Received #" + order.getOrderId();
        String adminBody = "Admin notification: A new order has been placed. Order details:\n" + body;
        EmailRequest adminEmailRequest = new EmailRequest(adminEmail, adminSubject, adminBody);
        emailService.sendHtmlEmail(adminEmailRequest);
    }
}
