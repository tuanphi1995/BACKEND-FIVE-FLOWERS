package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    private String paymentMethod;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime paymentDate;

    private boolean isAdminCreated;

    public boolean isAdminCreated() {
        return isAdminCreated;
    }

    public void setAdminCreated(boolean adminCreated) {
        isAdminCreated = adminCreated;
    }
}
