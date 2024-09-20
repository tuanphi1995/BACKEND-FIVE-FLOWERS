package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Day;
import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.entity.Itinerary;
import com.example.backendfiveflowers.entity.Trip;
import com.example.backendfiveflowers.service.HourService;
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
    @Autowired
    private HourService hourService;


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
    @PostMapping("/{itineraryId}/days")
    public ResponseEntity<Itinerary> addDayToItinerary(@PathVariable Long itineraryId, @RequestBody Day newDay) {
        Itinerary updatedItinerary = itineraryService.addDayToItinerary(itineraryId, newDay);
        return ResponseEntity.ok(updatedItinerary); // Trả về Itinerary đã cập nhật
    }
    @PostMapping("/{itineraryId}/days/{dayId}/hours")
    public ResponseEntity<Day> addHourToDay(@PathVariable Long itineraryId, @PathVariable Long dayId, @RequestBody Hour newHour) {
        Day updatedDay = itineraryService.addHourToDay(itineraryId, dayId, newHour);
        return ResponseEntity.ok(updatedDay); // Trả về ngày đã được cập nhật với giờ mới
    }


}
