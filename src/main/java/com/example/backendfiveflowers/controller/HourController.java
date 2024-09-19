package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.service.HourService;
import com.example.backendfiveflowers.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hours")
public class HourController {

    @Autowired
    private HourService hourService;

    @Autowired
    private TripService tripService;

    // Thêm hoặc cập nhật giờ (Hour)
    @PostMapping("/save/{dayId}")
    public ResponseEntity<Hour> saveOrUpdateHour(@PathVariable Long dayId, @RequestBody Hour hour) {
        Hour savedHour = hourService.saveOrUpdateHour(dayId, hour);
        return ResponseEntity.ok(savedHour);
    }

    // Sửa giờ (Hour) độc lập
    @PutMapping("/update/{hourId}")
    public ResponseEntity<Hour> updateHour(@PathVariable Long hourId, @RequestBody Hour updatedHour) {
        Hour updated = hourService.updateHour(hourId, updatedHour);
        return ResponseEntity.ok(updated);
    }

    // Lấy giờ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Hour> getHourById(@PathVariable Long id) {
        Hour hour = hourService.getHourById(id);
        return ResponseEntity.ok(hour);
    }

    // Xóa giờ theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHourById(@PathVariable Long id) {
        hourService.deleteHourById(id);
        return ResponseEntity.ok("Hour deleted successfully");
    }

    // Xóa nhiều giờ theo danh sách ID
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHoursByIds(@RequestBody List<Long> hourIds) {
        hourService.deleteHoursByIds(hourIds);
        return ResponseEntity.ok("Hours deleted successfully");
    }
    // Lấy tất cả các giờ
    @GetMapping("/all")
    public List<Hour> getAllHours() {
        return hourService.getAllHours();
    }


}
