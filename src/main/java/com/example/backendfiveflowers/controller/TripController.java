package com.example.backendfiveflowers.controller;
import com.example.backendfiveflowers.entity.Trip;
import com.example.backendfiveflowers.service.TripService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private ObjectMapper objectMapper;

    // API để thêm chuyến đi hoặc danh sách các chuyến đi
    @PostMapping("/add")
    public ResponseEntity<List<Trip>> addTrips(@RequestBody Object tripRequest) {
        List<Trip> savedTrips;

        // Kiểm tra xem request body là mảng hay đối tượng đơn
        if (tripRequest instanceof List) {
            List<Trip> trips = objectMapper.convertValue(tripRequest, new TypeReference<List<Trip>>() {});
            savedTrips = tripService.addTrips(trips);
        } else {
            Trip trip = objectMapper.convertValue(tripRequest, Trip.class);
            savedTrips = tripService.addTrips(Collections.singletonList(trip));
        }

        return ResponseEntity.ok(savedTrips);
    }

    // API để cập nhật danh sách các chuyến đi và tất cả các thực thể liên quan
    @PutMapping("/update")
    public ResponseEntity<List<Trip>> updateTrips(@RequestBody List<Trip> updatedTrips) {
        List<Long> tripIds = updatedTrips.stream().map(Trip::getId).toList();
        List<Trip> updated = tripService.updateTripsWithDetails(tripIds, updatedTrips);
        return ResponseEntity.ok(updated);
    }

    // API để lấy chuyến đi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTripsByIds(@RequestBody List<Long> tripIds) {
        tripService.deleteTripsByIds(tripIds);
        return ResponseEntity.ok("Trips deleted successfully");
    }

    // API để lấy tất cả chuyến đi theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Trip>> getTripsByUserId(@PathVariable Integer userId) {
        List<Trip> trips = tripService.getTripsByUserId(userId);
        return ResponseEntity.ok(trips);
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip updatedTrip) {
        Trip updated = tripService.updateTripWithDetails(id, updatedTrip);
        return ResponseEntity.ok(updated);
    }

}
