package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/stats/byDate")
    public ResponseEntity<Map<String, Object>> getStatsByDate(@RequestParam("date") String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse(dateStr, formatter);
        Map<String, Object> stats = analyticsService.getStatsByDate(date);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
