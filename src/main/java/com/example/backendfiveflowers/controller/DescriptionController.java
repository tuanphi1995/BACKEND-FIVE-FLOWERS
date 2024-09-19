package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Description;
import com.example.backendfiveflowers.service.DescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/descriptions")
public class DescriptionController {

    @Autowired
    private DescriptionService descriptionService;

    // Thêm hoặc cập nhật mô tả (Description)
    @PostMapping("/save/{hourId}")
    public ResponseEntity<Description> saveOrUpdateDescription(@PathVariable Long hourId, @RequestBody Description description) {
        Description savedDescription = descriptionService.saveOrUpdateDescription(hourId, description);
        return ResponseEntity.ok(savedDescription);
    }

    // Sửa mô tả (Description) độc lập
    @PutMapping("/update/{descriptionId}")
    public ResponseEntity<Description> updateDescription(@PathVariable Long descriptionId, @RequestBody Description updatedDescription) {
        Description updated = descriptionService.updateDescription(descriptionId, updatedDescription);
        return ResponseEntity.ok(updated);
    }

    // Lấy mô tả theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Description> getDescriptionById(@PathVariable Long id) {
        Description description = descriptionService.getDescriptionById(id);
        return ResponseEntity.ok(description);
    }

    // Xóa mô tả theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDescriptionById(@PathVariable Long id) {
        descriptionService.deleteDescriptionById(id);
        return ResponseEntity.ok("Description deleted successfully");
    }
}
