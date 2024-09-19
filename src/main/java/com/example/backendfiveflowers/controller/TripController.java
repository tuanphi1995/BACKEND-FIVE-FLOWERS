package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Trip;
import com.example.backendfiveflowers.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    // API để thêm chuyến đi cùng tất cả các thông tin liên quan
    @PostMapping("/add")
    public ResponseEntity<Trip> addTrip(@RequestBody Trip trip) {
        Trip savedTrip = tripService.addTripWithDetails(trip);
        return ResponseEntity.ok(savedTrip);
    }

    // API để cập nhật chuyến đi và tất cả các thực thể liên quan
    @PutMapping("/update/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip updatedTrip) {
        Trip updated = tripService.updateTripWithDetails(id, updatedTrip);
        return ResponseEntity.ok(updated);
    }

    // API để lấy chuyến đi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }

    // API để xóa chuyến đi theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTripById(@PathVariable Long id) {
        tripService.deleteTripById(id);
        return ResponseEntity.ok("Trip deleted successfully");
    }

    // API để lấy tất cả chuyến đi theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Trip>> getTripsByUserId(@PathVariable Integer userId) {
        List<Trip> trips = tripService.getTripsByUserId(userId);
        return ResponseEntity.ok(trips);
    }
}
