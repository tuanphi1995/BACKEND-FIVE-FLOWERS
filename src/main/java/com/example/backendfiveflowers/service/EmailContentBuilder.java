package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class EmailContentBuilder {

    public String buildOrderConfirmationEmail(Order order) {
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>Order Confirmation</h1>");
        builder.append("<p>Thank you for your order! Here are the details:</p>");
        builder.append("<ul>");
        builder.append("<li>Order ID: ").append(order.getOrderId()).append("</li>");
        builder.append("<li>Total Price: $").append(order.getPrice()).append("</li>");
        builder.append("<li>Shipping Cost: $").append(order.getShippingCost()).append("</li>");
        builder.append("</ul>");
        builder.append("<p><b>Order Details:</b></p>");
        builder.append("<table border='1'><tr><th>Product</th><th>Quantity</th><th>Price</th></tr>");

        order.getOrderDetails().forEach(detail -> {
            builder.append("<tr>");
            builder.append("<td>").append(detail.getProduct().getName()).append("</td>");
            builder.append("<td>").append(detail.getQuantity()).append("</td>");
            builder.append("<td>").append(detail.getPrice()).append("</td>");
            builder.append("</tr>");
        });

        builder.append("</table>");
        builder.append("<p>You can view your order <a href='http://yourwebsite.com/orders/")
                .append(order.getOrderId())
                .append("'>here</a>.</p>");
        builder.append("<p>If you have any questions, please contact us at support@example.com.</p>");
        return builder.toString();
    }
}
