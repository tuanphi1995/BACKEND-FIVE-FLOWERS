package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Expense;
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
    public void deleteHourById(Long id) {
        Hour hour = hourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + id));
        hourRepository.delete(hour);
    }

    // Xóa nhiều giờ theo danh sách ID
    public void deleteHoursByIds(List<Long> hourIds) {
        hourIds.forEach(this::deleteHourById);
    }


    // Lấy tất cả các giờ
    public List<Hour> getAllHours() {
        return hourRepository.findAll();
    }
    public Hour addExpenseToHour(Long hourId,Expense newExpense) {
        Hour hour = hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));

        // Thêm expense mới vào danh sách expenses của giờ
        newExpense.setHour(hour);
        hour.getExpenses().add(newExpense);

        return hourRepository.save(hour); // Lưu và trả về giờ đã cập nhật
    }
}
