package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/track")
    public ResponseEntity<Void> trackVisit(@RequestBody Map<String, String> payload, Authentication authentication) {
        String visitTimeStr = payload.get("visitTime");
        String page = payload.get("page");

        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));

            if (isAdmin) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime visitTime = LocalDateTime.parse(visitTimeStr, formatter);

        AnalyticsVisit visit = new AnalyticsVisit();
        visit.setVisitTime(visitTime);
        visit.setPage(page);

        analyticsService.saveVisit(visit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/visit-count")
    public ResponseEntity<Map<String, Integer>> getVisitCount(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int visitCount = analyticsService.getVisitCount(date);
        return new ResponseEntity<>(Collections.singletonMap("visitCount", visitCount), HttpStatus.OK);
    }
}
