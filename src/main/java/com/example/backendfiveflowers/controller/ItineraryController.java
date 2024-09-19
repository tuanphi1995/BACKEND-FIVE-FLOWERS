package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Itinerary;
import com.example.backendfiveflowers.entity.Trip;
import com.example.backendfiveflowers.service.ItineraryService;
import com.example.backendfiveflowers.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itineraries")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private TripService tripService;


    @GetMapping("/all")
    public List<Itinerary> getAllItineraries() {
        return itineraryService.getAllItineraries();
    }

    @PostMapping("/add/{tripId}")
    public ResponseEntity<Trip> addItineraryToTrip(@PathVariable Long tripId, @RequestBody List<Itinerary> itineraries) {
        Trip trip = tripService.addItineraryToTrip(tripId, itineraries);
        return ResponseEntity.ok(trip);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Itinerary> getItineraryById(@PathVariable Long id) {
        Itinerary itinerary = itineraryService.getItineraryById(id);
        return itinerary != null ? ResponseEntity.ok(itinerary) : ResponseEntity.notFound().build();
    }

    // Sửa lịch trình theo ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Itinerary> updateItinerary(@PathVariable Long id, @RequestBody Itinerary updatedItinerary) {
        Itinerary itinerary = itineraryService.updateItinerary(id, updatedItinerary);
        return ResponseEntity.ok(itinerary);
    }

        @DeleteMapping("/delete")
        public ResponseEntity<String> deleteItinerariesByIds(@RequestBody List<Long> itineraryIds) {
            tripService.deleteItinerariesByIds(itineraryIds);
            return ResponseEntity.ok("Itineraries deleted successfully");
        }

}
