package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.AnalyticsVisit;
import com.example.backendfiveflowers.repository.AnalyticsVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsVisitRepository visitRepository;

    public void saveVisit(AnalyticsVisit visit) {
        visitRepository.save(visit);
        System.out.println("Visit saved: " + visit); // Thêm log để kiểm tra
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, List<?>> getStats() {
        List<AnalyticsVisit> visits = visitRepository.findAll();
        Map<String, Long> visitCountByDate = visits.stream()
                .collect(Collectors.groupingBy(v -> v.getVisitTime().toLocalDate().toString(), Collectors.counting()));

        List<String> dates = new ArrayList<>(visitCountByDate.keySet());
        List<Long> totalVisits = new ArrayList<>(visitCountByDate.values());

        Map<String, List<?>> stats = new HashMap<>();
        stats.put("dates", dates);
        stats.put("totalVisits", totalVisits);

        return stats;
    }
}
