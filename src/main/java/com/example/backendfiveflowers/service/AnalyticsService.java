package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.repository.AnalyticsVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsVisitRepository visitRepository;

    public void saveVisit(AnalyticsVisit visit) {
        visitRepository.save(visit);
        System.out.println("Visit saved: " + visit);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, List<?>> getStats(LocalDateTime startDate, LocalDateTime endDate) {
        List<AnalyticsVisit> visits = visitRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        System.out.println("Fetching stats from " + startDate + " to " + endDate);

        List<AnalyticsVisit> filteredVisits = visits.stream()
                .filter(v -> !v.getVisitTime().isBefore(startDate) && !v.getVisitTime().isAfter(endDate))
                .collect(Collectors.toList());

        System.out.println("Filtered Visits: " + filteredVisits);

        Map<String, Long> visitCountByPeriod;
        long daysBetween = java.time.Duration.between(startDate, endDate).toDays();

        if (daysBetween < 7) {
            visitCountByPeriod = filteredVisits.stream()
                    .collect(Collectors.groupingBy(v -> {
                        LocalDateTime visitTime = v.getVisitTime().withMinute(0).withSecond(0).withNano(0);
                        return visitTime.format(formatter);
                    }, Collectors.counting()));
        } else {
            visitCountByPeriod = filteredVisits.stream()
                    .collect(Collectors.groupingBy(v -> {
                        LocalDateTime visitTime = v.getVisitTime().withHour(0).withMinute(0).withSecond(0).withNano(0);
                        return visitTime.toLocalDate().toString();
                    }, Collectors.counting()));
        }

        List<String> times = new ArrayList<>(visitCountByPeriod.keySet());
        List<Long> totalVisits = new ArrayList<>(visitCountByPeriod.values());

        System.out.println("Times: " + times);
        System.out.println("Total Visits: " + totalVisits);

        Map<String, List<?>> stats = new HashMap<>();
        stats.put("times", times);
        stats.put("totalVisits", totalVisits);

        return stats;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, List<?>> getYesterdayStats() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = LocalDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return getStats(startDate, endDate);
    }
}
