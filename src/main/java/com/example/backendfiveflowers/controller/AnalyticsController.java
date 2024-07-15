package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/track")
    public ResponseEntity<Void> trackVisit(@RequestBody AnalyticsVisit visit) {
        System.out.println("Received visit tracking request: " + visit);
        analyticsService.saveVisit(visit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, List<?>>> getStats() {
        Map<String, List<?>> stats = analyticsService.getStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
