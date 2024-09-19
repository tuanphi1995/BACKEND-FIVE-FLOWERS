package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Day;
import com.example.backendfiveflowers.service.DayService;
import com.example.backendfiveflowers.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/days")
public class DayController {

    @Autowired
    private DayService dayService;

    @Autowired
    private TripService tripService; ;

    // Thêm hoặc cập nhật ngày (Day)
    @PostMapping("/save/{itineraryId}")
    public ResponseEntity<Day> saveOrUpdateDay(@PathVariable Long itineraryId, @RequestBody Day day) {
        Day savedDay = dayService.saveOrUpdateDay(itineraryId, day);
        return ResponseEntity.ok(savedDay);
    }

    // Sửa ngày (Day) độc lập
    @PutMapping("/update/{dayId}")
    public ResponseEntity<Day> updateDay(@PathVariable Long dayId, @RequestBody Day updatedDay) {
        Day updated = dayService.updateDay(dayId, updatedDay);
        return ResponseEntity.ok(updated);
    }

    // Lấy ngày theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Day> getDayById(@PathVariable Long id) {
        Day day = dayService.getDayById(id);
        return ResponseEntity.ok(day);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDayById(@PathVariable Long id) {
        dayService.deleteDayById(id);
        return ResponseEntity.ok("Day deleted successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDaysByIds(@RequestBody List<Long> dayIds) {
        dayService.deleteDaysByIds(dayIds);
        return ResponseEntity.ok("Days deleted successfully");
    }


    // Lấy tất cả các ngày
    @GetMapping("/all")
    public List<Day> getAllDays() {
        return dayService.getAllDays();
    }
}
