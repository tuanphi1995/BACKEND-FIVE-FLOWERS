package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.repository.HourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HourService {
    @Autowired
    private HourRepository hourRepository;

    public List<Hour> getAllHours() {
        return hourRepository.findAll();
    }

    public Hour saveHour(Hour hour) {
        return hourRepository.save(hour);
    }

    public void deleteHour(Long id) {
        hourRepository.deleteById(id);
    }

    public Hour getHourById(Long id) {
        return hourRepository.findById(id).orElse(null);
    }
}

