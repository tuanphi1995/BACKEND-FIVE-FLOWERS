package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Itinerary;
import com.example.backendfiveflowers.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itineraries")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @GetMapping("/all")
    public List<Itinerary> getAllItineraries() {
        return itineraryService.getAllItineraries();
    }

    @PostMapping("/save")
    public Itinerary saveItinerary(@RequestBody Itinerary itinerary) {
        return itineraryService.saveItinerary(itinerary);
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

    // Xóa lịch trình theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItineraryById(@PathVariable Long id) {
        itineraryService.deleteItineraryById(id);
        return ResponseEntity.ok("Itinerary deleted successfully");
    }
}
