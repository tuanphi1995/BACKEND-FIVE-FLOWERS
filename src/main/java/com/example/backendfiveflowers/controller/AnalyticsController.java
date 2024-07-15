package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/track")
    public ResponseEntity<Void> trackVisit(@RequestBody AnalyticsVisit visit) {
        analyticsService.saveVisit(visit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, List<?>>> getStats() {
        Map<String, List<?>> stats = analyticsService.getStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
