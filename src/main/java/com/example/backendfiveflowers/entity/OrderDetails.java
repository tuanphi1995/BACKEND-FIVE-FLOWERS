package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Products product;

    private int quantity;
    private BigDecimal price;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailId implements Serializable {
        private Long order_id;
        private Long product_id;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderDetailId that = (OrderDetailId) o;
            return Objects.equals(order_id, that.order_id) && Objects.equals(product_id, that.product_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(order_id, product_id);
        }
    }
}
