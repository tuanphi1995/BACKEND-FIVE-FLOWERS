package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class EmailContentBuilder {

    public String buildOrderConfirmationEmail(Order order) {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>");
        builder.append("<html><head><style>")
                .append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }")
                .append(".email-container { width: 100%; max-width: 600px; margin: auto; }")
                .append(".email-header, .email-footer { background-color: #f4f4f4; padding: 10px; text-align: center; }")
                .append(".email-body { padding: 20px; }")
                .append(".email-body h1 { color: #007bff; }")
                .append(".order-details { width: 100%; border-collapse: collapse; margin-top: 20px; }")
                .append(".order-details th, .order-details td { border: 1px solid #ddd; padding: 8px; }")
                .append(".order-details th { background-color: #f4f4f4; }")
                .append(".button-container { text-align: center; margin: 20px 0; }")
                .append(".button { display: inline-block; padding: 10px 20px; font-size: 16px; font-weight: bold; color: #000; background-color: #ff6600; border-radius: 5px; text-decoration: none; }")
                .append(".button:hover { background-color: #cc5200; }")
                .append("</style></head><body>");
        builder.append("<div class='email-container'>")
                .append("<div class='email-header'><h1>Order Confirmation</h1></div>")
                .append("<div class='email-body'>")
                .append("<p>Thank you for your order! Here are the details:</p>")
                .append("<ul>")
                .append("<li>Order ID: ").append(order.getOrderId()).append("</li>")
                .append("<li>Total Price: $").append(order.getPrice()).append("</li>")
                .append("<li>Shipping Cost: $").append(order.getShippingCost()).append("</li>")
                .append("</ul>")
                .append("<h2>Order Details:</h2>")
                .append("<table class='order-details'>")
                .append("<tr><th>Product</th><th>Quantity</th><th>Price</th></tr>");

        order.getOrderDetails().forEach(detail -> {
            builder.append("<tr>")
                    .append("<td>").append(detail.getProduct().getName()).append("</td>")
                    .append("<td>").append(detail.getQuantity()).append("</td>")
                    .append("<td>").append(detail.getPrice()).append("</td>")
                    .append("</tr>");
        });

        builder.append("</table>")
                .append("<div class='button-container'>")
                .append("<a href='http://localhost:3000/cart-user' class='button'>View Order</a>")
                .append("</div>")
                .append("<p>If you have any questions, please contact us at support@ecowheels.com.</p>")
                .append("</div>")
                .append("<div class='email-footer'><p>&copy; 2024 Bike Club . All rights reserved.</p></div>")
                .append("</div>")
                .append("</body></html>");
        return builder.toString();
    }
}
