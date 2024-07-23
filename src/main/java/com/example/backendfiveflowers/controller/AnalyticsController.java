package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/track")
    public ResponseEntity<Void> trackVisit(@RequestBody Map<String, String> payload) {
        String visitTimeStr = payload.get("visitTime");
        String page = payload.get("page");

        System.out.println("Received visit tracking request with payload: " + payload);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime visitTime = LocalDateTime.parse(visitTimeStr, formatter);

        AnalyticsVisit visit = new AnalyticsVisit();
        visit.setVisitTime(visitTime);
        visit.setPage(page);

        System.out.println("Parsed AnalyticsVisit object: " + visit);

        analyticsService.saveVisit(visit);
        System.out.println("Visit saved: " + visit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, List<?>>> getStats(@RequestParam("startDate") String startDateStr, @RequestParam("endDate") String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
        Map<String, List<?>> stats = analyticsService.getStats(startDate, endDate);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/stats/yesterday")
    public ResponseEntity<Map<String, List<?>>> getYesterdayStats() {
        Map<String, List<?>> stats = analyticsService.getYesterdayStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}