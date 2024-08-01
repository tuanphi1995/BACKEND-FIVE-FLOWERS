package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.repository.AnalyticsVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsVisitRepository visitRepository;

    public void saveVisit(AnalyticsVisit visit) {
        visitRepository.save(visit);
        System.out.println("Visit saved: " + visit);
    }

    public int getVisitCount(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        System.out.println("Start of day: " + startOfDay);
        System.out.println("End of day: " + endOfDay);
        return visitRepository.countByVisitTimeBetween(startOfDay, endOfDay);
    }

    public List<AnalyticsVisit> getAllVisits() {
        return visitRepository.findAll();
    }
}
