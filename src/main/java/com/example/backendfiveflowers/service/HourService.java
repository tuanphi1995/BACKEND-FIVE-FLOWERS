package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.entity.Day;
import com.example.backendfiveflowers.repository.HourRepository;
import com.example.backendfiveflowers.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HourService {

    @Autowired
    private HourRepository hourRepository;

    @Autowired
    private DayRepository dayRepository;

    // Thêm hoặc cập nhật giờ (Hour) cho một ngày
    public Hour saveOrUpdateHour(Long dayId, Hour hour) {
        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + dayId));
        hour.setDay(day);  // Gán ngày cho giờ
        return hourRepository.save(hour);
    }

    // Sửa giờ (Hour) độc lập
    public Hour updateHour(Long hourId, Hour updatedHour) {
        Hour existingHour = hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));

        if (updatedHour.getTime() != null) {
            existingHour.setTime(updatedHour.getTime());
        }

        return hourRepository.save(existingHour);
    }

    // Lấy giờ (Hour) theo ID
    public Hour getHourById(Long hourId) {
        return hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));
    }

    // Xóa giờ (Hour) theo ID
    public void deleteHourById(Long hourId) {
        Hour hour = hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));
        hourRepository.delete(hour);
    }

    // Lấy tất cả các giờ
    public List<Hour> getAllHours() {
        return hourRepository.findAll();
    }
}
