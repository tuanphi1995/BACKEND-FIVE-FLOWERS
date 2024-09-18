package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Day;
import com.example.backendfiveflowers.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DayService {
    @Autowired
    private DayRepository dayRepository;

    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }

    public Day saveDay(Day day) {
        return dayRepository.save(day);
    }

    public void deleteDay(Long id) {
        dayRepository.deleteById(id);
    }

    public Day getDayById(Long id) {
        return dayRepository.findById(id).orElse(null);
    }
}
