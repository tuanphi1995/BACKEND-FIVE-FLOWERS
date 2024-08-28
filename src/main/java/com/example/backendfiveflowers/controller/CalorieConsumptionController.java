package com.example.backendfiveflowers.controller;


import com.example.backendfiveflowers.entity.CalorieConsumption;
import com.example.backendfiveflowers.service.CalorieConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calorie-consumption")
public class CalorieConsumptionController {

    @Autowired
    private CalorieConsumptionService calorieConsumptionService;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CalorieConsumption>> getAll(@PathVariable Integer id){
        return calorieConsumptionService.getAllByUserId(id);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CalorieConsumption> post (@RequestBody CalorieConsumption calorieConsumption){
        return calorieConsumptionService.save(calorieConsumption);
    }
}
