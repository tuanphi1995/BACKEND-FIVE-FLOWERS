package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CalorieConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int caloId;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private UserInfo userInfo;

    private double weight;
    private double distance; // km
    private double time; // minutes
    private double caloriesBurned;
    private LocalDateTime createdAt;

    // Private method to calculate calories burned
    private double calculateCalories(double weight, double distance, double time) {
        double speed = distance / (time / 60);
        double times = time / 60;
        double metValue = getMET(speed);
        return weight * metValue * times * 1.05;
    }

    // Private method to determine MET value based on speed
    private double getMET(double speed) {
        if (speed < 8) {
            return 6.0;
        } else if (speed < 10) {
            return 8.0;
        } else if (speed < 12) {
            return 10.0;
        } else {
            return 12.0;
        }

    }

    // Public method to calculate and set the caloriesBurned
    public void calculateCalories() {
        this.caloriesBurned = calculateCalories(this.weight, this.distance, this.time);
        this.createdAt = LocalDateTime.now(); // Optional: set the createdAt timestamp
    }
}
